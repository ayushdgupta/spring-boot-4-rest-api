package com.guptaji.springboot_learning.service;

import com.guptaji.springboot_learning.entity.User;
import com.guptaji.springboot_learning.model.UserLoginDto;

public interface JwtService {

    String generateToken(UserLoginDto user);
}
