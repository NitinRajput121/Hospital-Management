package com.hms.user;

import com.hms.user.entity.Role;
import com.hms.user.entity.Rolename;
import com.hms.user.entity.User;
import com.hms.user.repositry.RoleRepo;
import com.hms.user.repositry.UserRepo;
import com.hms.user.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Bean
    public CommandLineRunner seedAdmin() {
        return args -> {
            if (!userRepo.findByEmail("admin@hms.com").isPresent()) {
                Role adminRole = roleRepo.findByName(Rolename.ADMIN)
                        .orElseThrow(() -> new RuntimeException("Admin role not found"));



                User admin = User.builder()
                        .name("Super Admin")
                        .email("admin@hms.com")
                        .password(passwordEncoder.encode("admin123")) // strong password!
                        .roles(new HashSet<>(Arrays.asList(adminRole)))
                        .build();

                admin = userRepo.save(admin);

                // create refresh token for admin
                refreshTokenService.create(admin.getEmail());

                System.out.println("✅ Admin user created with email=admin@hms.com, password=admin123");
            }
        };
    }
}

