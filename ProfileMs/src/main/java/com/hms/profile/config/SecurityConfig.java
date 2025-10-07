package com.hms.profile.config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final HeaderAuthFilter headerAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless API
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(headerAuthFilter, UsernamePasswordAuthenticationFilter.class) // 👈 Add your custom filter
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/profile/patient/create").permitAll()   // exact path Feign calls
                        .requestMatchers("/profile/**").authenticated()
                        .anyRequest().permitAll()
                );

                 return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // We don't actually use it, but this prevents Spring from creating the default one
        return username -> null;
    }
}

