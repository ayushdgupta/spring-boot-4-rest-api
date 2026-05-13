package com.guptaji.springboot_learning.service.impl;

import com.guptaji.springboot_learning.entity.User;
import com.guptaji.springboot_learning.model.UserPrincipal;
import com.guptaji.springboot_learning.repositories.UserRepo;
import com.guptaji.springboot_learning.service.DbUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class DbUserDetailsServiceImpl implements DbUserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(DbUserDetailsServiceImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> existingUser = userRepo.findByName(username);
        if (existingUser.isEmpty()){
            LOG.info("User {} not present", username);
            throw new UsernameNotFoundException("User "+username+" not found");
        }

        LOG.info("User {} verified", username);
        return new UserPrincipal(existingUser.get());
    }
}
