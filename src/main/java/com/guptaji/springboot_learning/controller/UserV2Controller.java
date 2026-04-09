package com.guptaji.springboot_learning.controller;

import com.guptaji.springboot_learning.entity.User;
import com.guptaji.springboot_learning.entity.UserDto;
import com.guptaji.springboot_learning.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// URI-based API Versioning
@RestController
@RequestMapping(path = "/userApi/v{ver}", version = "2.0.0")
public class UserV2Controller {

    private static final Logger LOG = LoggerFactory.getLogger(UserV2Controller.class);

    @Autowired
    private UserServiceImpl userService;

    // URL http://localhost:8081/userApi/v2.0.1/getAllUser
    @GetMapping(path = "/getAllUser")
    public ResponseEntity<?> fetchAllUserV21(){
        LOG.info("Extracting all users from DB from v2.0.0");
        List<User> users = userService.extractAllUsers();
        List<UserDto> userDtoList = users.stream().map(UserDto::new).toList();
        if (users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(userDtoList, HttpStatus.OK);
        }
    }

    // URL http://localhost:8081/userApi/v2.0.2/getAllUser
    @GetMapping(path = "/getAllUser", version = "2.0.1")
    public ResponseEntity<?> fetchAllUserV22(){
        LOG.info("Extracting all users from DB from v2.0.1");
        List<User> users = userService.extractAllUsers();
        List<UserDto> userDtoList = users.stream().map(UserDto::new).toList();
        if (users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(userDtoList, HttpStatus.OK);
        }
    }
}
