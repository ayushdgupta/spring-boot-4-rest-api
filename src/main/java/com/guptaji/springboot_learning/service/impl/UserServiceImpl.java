package com.guptaji.springboot_learning.service.impl;

import com.guptaji.springboot_learning.entity.User;
import com.guptaji.springboot_learning.repositories.UserRepo;
import com.guptaji.springboot_learning.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UsersUtility usersUtility;

    @Override
    public void addUser(User user) {
        User persistedUser = userRepo.save(user);
        LOG.info("Persisted user with id "+persistedUser.getId());
    }

    @Override
    public List<User> extractAllUsers() {
        List<User> allUsers = userRepo.findAll();
        if (allUsers != null && !allUsers.isEmpty()){
            Collections.sort(allUsers);
        }
        return allUsers;
    }

    @Override
    public User extractUserById(String id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public boolean updateUserNameById(String id, String name) {

        // M1 -> one way to update the data is to use save method but that will update the entire data
        // apart from key, but we need to update the name only so that's why we used native query
        int count = userRepo.updateNameById(name, id);
        LOG.info("Count of members updated {}", count);
        return count > 0;
    }

    @Override
    public boolean deleteUserById(String id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null){
            LOG.info("No member present with ID {}", id);
            return false;
        } else {
            userRepo.deleteById(id);
            return true;
        }
    }

    @Override
    public void processAllUsers() {
        List<User> allUsers = userRepo.findAll();
        allUsers.forEach(user -> usersUtility.processUser(user));
    }

}
