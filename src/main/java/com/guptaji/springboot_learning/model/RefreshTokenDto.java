package com.guptaji.springboot_learning.model;

import java.io.Serializable;

public class RefreshTokenDto implements Serializable {

    private String userName;
    private String refreshToken;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
