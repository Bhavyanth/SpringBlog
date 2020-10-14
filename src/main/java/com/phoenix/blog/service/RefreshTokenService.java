package com.phoenix.blog.service;

import com.phoenix.blog.exceptions.SpringPostException;
import com.phoenix.blog.Bean.RefreshToken;
import com.phoenix.blog.DAO.RefreshTokenDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private RefreshTokenDAO refreshTokenDAO;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenDAO.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        refreshTokenDAO.findByToken(token)
                .orElseThrow(() -> new SpringPostException("Invalid token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenDAO.deleteByToken(token);
    }
}
