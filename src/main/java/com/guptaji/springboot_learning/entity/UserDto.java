package com.guptaji.springboot_learning.entity;

public class UserDto {

    private String name;
    private String userType;

    public UserDto() {
        // default constructor
    }

    public UserDto(User user) {
        this.name = user.getName();
        this.userType = user.getUserType();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
