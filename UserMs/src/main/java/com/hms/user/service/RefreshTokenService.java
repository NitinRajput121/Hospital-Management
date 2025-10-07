package com.hms.user.service;

import com.hms.user.entity.RefreshToken;
import com.hms.user.entity.User;
import com.hms.user.exception.UserNotFoundException;
import com.hms.user.repositry.RefreshTokenRepository;
import com.hms.user.repositry.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepo userRepo;

    @Transactional
    public RefreshToken create(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElse(null);

        if (refreshToken == null) {
            refreshToken = new RefreshToken();
            refreshToken.setUser(user);
        }

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(24 * 60 * 1000));

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

}
