package com.hms.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.List;

@Component
public class TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);

    @Value("${app.jwt.secret}")
    private String secret;

    public TokenFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            if (path.equals("/user/login") || path.equals("/user/create")) {
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // Check for missing token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return this.onError(exchange, "Authentication header is missing", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            try {
                //Java’s cryptography object that represents the key used for signing/verifying the JWT.
                SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String userId = claims.getSubject();
                List<String> roles = claims.get("roles", List.class);

//                log.info("✅ Authenticated user: {} with roles: {}", userId, roles);

                // Forward claims to downstream services
                if (roles != null && !roles.isEmpty()) {
                    exchange = exchange.mutate()
                            .request(r -> r.headers(headers -> {
                                headers.add("X-User-Id", userId);
                                headers.add("X-Roles", String.join(",", roles));
                            }))
                            .build();
                }

                log.info("🔥 Successfully added headers for downstream services");

            } catch (Exception e) {
                log.error("❌ JWT parsing failed: {}", e.getMessage(), e);
                return this.onError(exchange, "Invalid or expired token", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        log.error("❌ Security error: {}", err);

        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");

        String body = "{\"error\": \"" + err + "\"}";

        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(body.getBytes())));
    }

    public static class Config {
    }
}
