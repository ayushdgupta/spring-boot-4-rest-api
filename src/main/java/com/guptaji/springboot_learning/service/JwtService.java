package com.guptaji.springboot_learning.service;

import com.guptaji.springboot_learning.entity.User;

public interface JwtService {

    String generateToken(User user);
}
