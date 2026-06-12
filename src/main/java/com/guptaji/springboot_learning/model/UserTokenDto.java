package com.guptaji.springboot_learning.model;

public class UserTokenDto {

    private String name;
    private String JwtToken;
    private String refreshToken;

    public UserTokenDto() {
        // default
    }

    public UserTokenDto(String name, String jwtToken, String refreshToken) {
        this.name = name;
        JwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJwtToken() {
        return JwtToken;
    }

    public void setJwtToken(String jwtToken) {
        JwtToken = jwtToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
