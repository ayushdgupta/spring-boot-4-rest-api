package com.guptaji.springboot_learning.util;

import com.guptaji.springboot_learning.constant.UserRoles;
import com.guptaji.springboot_learning.entity.User;
import com.guptaji.springboot_learning.model.UserLoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static com.guptaji.springboot_learning.constant.UserRoles.ROLE_USER;

public class CommonUtility {

    private static final Logger LOG = LoggerFactory.getLogger(CommonUtility.class);

    public static Authentication verifyUser(AuthenticationManager authenticationManager, UserLoginDto user) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        return authenticate;
    }
}
