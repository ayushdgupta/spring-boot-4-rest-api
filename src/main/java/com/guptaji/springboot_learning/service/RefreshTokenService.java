package com.guptaji.springboot_learning.service;

import com.guptaji.springboot_learning.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken findRefreshTokenByUserName(String userName);

    void deleteExistingToken(RefreshToken refreshTokenExpired);
    boolean isRefreshTokenExpired(RefreshToken refreshToken);

    String generateRefreshToken(String userName);
}
