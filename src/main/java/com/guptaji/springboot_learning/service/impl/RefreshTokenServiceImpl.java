package com.guptaji.springboot_learning.service.impl;

import com.guptaji.springboot_learning.entity.RefreshToken;
import com.guptaji.springboot_learning.repositories.RefreshTokenRepo;
import com.guptaji.springboot_learning.repositories.UserRepo;
import com.guptaji.springboot_learning.service.RefreshTokenService;
import com.guptaji.springboot_learning.util.CommonUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final Logger LOG = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public RefreshToken findRefreshTokenByUserName(String userName) {
        Optional<RefreshToken> refreshToken = refreshTokenRepo.findByUserName(userName);
        return refreshToken.orElse(null);
    }

    @Override
    public void deleteExistingToken(RefreshToken refreshTokenExpired) {
        refreshTokenRepo.deleteById(refreshTokenExpired.getId());
    }

    @Override
    public boolean isRefreshTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpirationTime().isBefore(CommonUtility.currentTimestamp());
    }

    @Override
    public String generateRefreshToken(String userName) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUserName(userName);
        refreshToken.setExpirationTime(CommonUtility.currentTimestamp().plusMinutes(10L));
        refreshToken.setUser(userRepo.findByName(userName).get());
        refreshTokenRepo.save(refreshToken);
        return refreshToken.getToken();
    }
}
