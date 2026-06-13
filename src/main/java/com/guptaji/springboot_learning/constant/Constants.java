package com.guptaji.springboot_learning.constant;

public class Constants {

    public static final String EMP_CACHE_NAME = "employee-cache";
    public static final String CSRF_KEY = "_csrf";
    public static final String CSRF_TOKEN_HEADER = "X-CSRF-TOKEN";
    public static final String SIGN_UP = "/sign-up";
    public static final String SIGN_UP_PATTERN = "/userApi/v1.0.0/sign-up";
    public static final String LOGIN = "/login";
    public static final String REFRESH_TOKEN = "/refresh-token";
    public static final String REFRESH_TOKEN_PATTERN = "/userApi/v1.0.0/refresh-token";
    public static final String LOGIN_PATTERN = "/userApi/v1.0.0/login";
    public static final String KEY_GEN_ALGO = "HmacSHA256";
    public static final String ROLE = "role";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";
    public static final String SWAGGER_UI_URL = "/swagger-ui/**";
    public static final String SWAGGER_API_UI_URL = "/v3/api-docs/**";
}
