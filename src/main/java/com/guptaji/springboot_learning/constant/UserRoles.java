package com.guptaji.springboot_learning.constant;

public enum UserRoles {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private String role;

    UserRoles(String role){
        this.role = role;
    }

    public static boolean allowedRoles(String role) {

        for (UserRoles roles : values()){
            if (roles.getRole().equalsIgnoreCase(role)){
                return true;
            }
        }

        return false;
    }

    public String getRole() {
        return role;
    }
}
