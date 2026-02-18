package com.guptaji.springboot_learning.service;

import com.guptaji.springboot_learning.entity.User;

import java.util.List;

public interface UserService {

    void addUser(User user);
    List<User> extractAllUsers();
    User extractUserById(String id);
    boolean updateUserNameById(String id, String name);
    boolean deleteUserById(String id);
    void processAllUsers();
}
