package com.guptaji.springboot_learning.constant;

public enum UserRoles {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private String role;

    UserRoles(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
