package com.guptaji.springboot_learning.model;

import java.io.Serializable;

public class UserLoginDto implements Serializable {

    private String userName;
    private String password;
    private String userType;

    public String getUserName() {
        return userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
