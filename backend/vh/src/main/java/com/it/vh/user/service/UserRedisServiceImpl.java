package com.it.vh.user.service;

import com.it.vh.user.domain.entity.RefreshToken;
import com.it.vh.user.domain.repository.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserRedisServiceImpl implements UserRedisService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Value("${spring.redis.ttl.refresh_token}")
    private long refresh_expiration;

    @Override
    @Transactional
    public void saveRefreshToken(String userId, String refreshToken) {
        RefreshToken token = RefreshToken.builder()
            .id(userId)
            .refreshToken(refreshToken)
            .ttl(refresh_expiration)
            .build();
        refreshTokenRedisRepository.save(token);
    }

    @Override
    public RefreshToken getRefreshToken(String userId) {
        return refreshTokenRedisRepository.findById(userId)
            .orElse(null);
    }

    @Override
    public void deleteRefreshToken(String userId) {
        RefreshToken token = RefreshToken.builder()
            .id(userId)
            .build();
        refreshTokenRedisRepository.delete(token);
    }
}
