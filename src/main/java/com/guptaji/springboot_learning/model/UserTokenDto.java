package com.guptaji.springboot_learning.model;

public class UserTokenDto {

    private String name;
    private String token;

    public UserTokenDto() {
        // default
    }

    public UserTokenDto(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
