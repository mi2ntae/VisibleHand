package com.it.vh.user.service;

import com.it.vh.user.domain.entity.RefreshToken;

public interface UserRedisService {

    void saveRefreshToken(String userId, String refreshToken);

    RefreshToken getRefreshToken(String userId);

    void deleteRefreshToken(String userId);

}
