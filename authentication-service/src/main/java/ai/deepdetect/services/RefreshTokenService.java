package ai.deepdetect.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ai.deepdetect.entities.RefreshTokenEntity;
import ai.deepdetect.exceptions.OTPExpiredException;
import ai.deepdetect.repositories.RefreshTokenRepository;
import ai.deepdetect.utils.DateTimeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${application.jwt.refreshExpiration}")
    private long refreshTokenExpirationTime;

    public RefreshTokenEntity createRefreshToken(int userId) {
        String token = UUID.randomUUID().toString();

        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity
            .builder()
            .userId(userId)
            .refreshToken(token)
            .expireDate(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
            .build();

        return refreshTokenRepository.save(refreshTokenEntity);
    }

    @Transactional
    public RefreshTokenEntity refreshToken(String token) {
        RefreshTokenEntity byRefreshToken = refreshTokenRepository.findByRefreshToken(token);
        int userId = byRefreshToken.getId();

        try {
            DateTimeUtils.isTokenInTime(byRefreshToken.getExpireDate());
            byRefreshToken.setExpireDate(new Date(System.currentTimeMillis() + refreshTokenExpirationTime));
            return refreshTokenRepository.save(byRefreshToken);
        } catch (OTPExpiredException e) {
            RefreshTokenEntity newRefreshToken = createRefreshToken(userId);
            refreshTokenRepository.delete(byRefreshToken);

            return newRefreshToken;
        }
    }

}
