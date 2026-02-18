package com.guptaji.springboot_learning.service.impl;

import com.guptaji.springboot_learning.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserHelper {

    private static final Logger LOG = LoggerFactory.getLogger(UsersUtility.class);

    private String prefix;
    private String suffix;

    public UserHelper(String prefix, String suffix){
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public User processUser(User user){
        user.setName(prefix + " " + user.getName() + " " + suffix);
        return user;
    }
}
