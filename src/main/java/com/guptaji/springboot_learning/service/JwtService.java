package com.guptaji.springboot_learning.service;

import com.guptaji.springboot_learning.entity.User;
import com.guptaji.springboot_learning.model.UserLoginDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(UserLoginDto user);
    String extractUserName(String token);
    boolean validateToken(String token, UserDetails userDetails);
}
