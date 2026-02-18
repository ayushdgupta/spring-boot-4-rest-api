package com.guptaji.springboot_learning.service.impl;

import com.guptaji.springboot_learning.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersUtility {

    private static final Logger LOG = LoggerFactory.getLogger(UsersUtility.class);

    private UserHelper userHelper;

    public UsersUtility(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    public User processUser(User user){
        user = this.userHelper.processUser(user);
        LOG.info("processed user {}", user);
        return user;
    }
}
