package com.guptaji.springboot_learning.controller;

import com.guptaji.springboot_learning.constant.UserRoles;
import com.guptaji.springboot_learning.entity.RefreshToken;
import com.guptaji.springboot_learning.entity.User;
import com.guptaji.springboot_learning.entity.UserDto;
import com.guptaji.springboot_learning.model.RefreshTokenDto;
import com.guptaji.springboot_learning.model.UserLoginDto;
import com.guptaji.springboot_learning.model.UserTokenDto;
import com.guptaji.springboot_learning.service.impl.JwtServiceImpl;
import com.guptaji.springboot_learning.service.impl.RefreshTokenServiceImpl;
import com.guptaji.springboot_learning.service.impl.UserServiceImpl;
import com.guptaji.springboot_learning.util.CommonUtility;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.guptaji.springboot_learning.constant.Constants.*;

// http://localhost:8081/swagger-ui/index.html
@RestController
@RequestMapping(path = "/userApi/v{ver}", version = "1.0.0")
//@RequestMapping(path = "/userApi/", version = "1.0.0")  this is used for header versioning
//@RequestMapping(path = "/userApi/", params = "version=1.0.0") this is used for query param based versioning
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;

    @PostMapping("/addUser")
    public ResponseEntity<?> createUser(@RequestBody User user){
        LOG.info("Request received to create an user "+user);
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(path = "/getAllUser")
    public ResponseEntity<?> fetchAllUser(){
        LOG.info("Extracting all users from DB from v1.0.0");
        List<User> users = userService.extractAllUsers();
        if (users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/getAllUser", version = "1.0.1")
    public ResponseEntity<?> fetchAllUserForHeaderVersioning(){
        LOG.info("Extracting all users from DB from v1.0.1");
        List<User> users = userService.extractAllUsers();
        if (users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    // get the data using path variable
    // localhost:8081/userApi/getById/4
    @GetMapping("/getById/{userId}")
    public ResponseEntity<?> getById(@PathVariable("userId") String id){
        LOG.info("Extracting user from DB using path variable for the id {}", id);
        User user = userService.extractUserById(id);
        if (user == null){
            LOG.info("No user found with id {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    // get the data by using query variable
    // localhost:8081/userApi/getByIdWithQueryVariable?userId=4
    @GetMapping("/getByIdWithQueryVariable")
    public ResponseEntity<?> getByIdUsingQueryVariable(@RequestParam("userId") String id){
        LOG.info("Extracting user from DB using query variable for the id {}", id);
        User user = userService.extractUserById(id);
        if (user == null){
            LOG.info("No user found with id {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    // get the data by using query variable
    @PutMapping("/updateById/{userId}/{userName}")
    public ResponseEntity<?> updateNameById(@PathVariable("userId") String id,
                                            @PathVariable("userName") String name){
        LOG.info("Update user name in DB for the id {}", id);
        boolean updated = userService.updateUserNameById(id, name);
        if (updated){
            LOG.info("Updated user name {} with id {}", name, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteById/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") String id){
        LOG.info("Delete user in DB for the id {}", id);
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted){
            LOG.info("Deleted user with id {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/useOfBean")
    public ResponseEntity<?> processAllUsers(){
        userService.processAllUsers();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<?> signUpUser(@RequestBody User user){

        LOG.info("Verifying the role");
        String userRole = user.getUserType();
        if (UserRoles.allowedRoles(userRole)){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            LOG.info("Encoded the password {}", user.getPassword());
            LOG.info("Creating an user");
            userService.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            LOG.info("Role {} is not allowed now", userRole);
            return new ResponseEntity<>("Passed role is not allowed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto user){

        LOG.info("Verifying the user {}", user.getUserName());
        try {
            Authentication authentication = CommonUtility.verifyUser(authenticationManager, user);
            if (authentication.isAuthenticated()){
                LOG.info("Verified user {}", user.getUserName());
                String jwtToken = jwtService.generateToken(user);
                RefreshToken refreshTokenByUserName = refreshTokenService.findRefreshTokenByUserName(user.getUserName());
                String refreshToken = null;
                if (refreshTokenByUserName == null){
                    LOG.info("No existing refresh token found need to create new one");
                } else {
                    boolean refreshTokenExpired = refreshTokenService.isRefreshTokenExpired(refreshTokenByUserName);
                    if (refreshTokenExpired){
                        LOG.info("Refresh token exist but expired so deleting the existing one");
                        refreshTokenService.deleteExistingToken(refreshTokenByUserName);
                    } else {
                        LOG.info("Refresh token exist and valid");
                        refreshToken = refreshTokenByUserName.getToken();
                    }
                }

                if (refreshToken == null){
                    refreshToken = refreshTokenService.generateRefreshToken(user.getUserName());
                }
                return new ResponseEntity<>(new UserTokenDto(user.getUserName(), jwtToken, refreshToken),
                        HttpStatus.OK);
            } else {
                LOG.info("user {} is not present", user.getUserName());
                return new ResponseEntity<>("You are not an existing user", HttpStatus.NOT_FOUND);
            }
        } catch (BadCredentialsException e){
            LOG.info("user {} is not present", user.getUserName());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(REFRESH_TOKEN)
    public ResponseEntity<?> generateRefreshToken(@RequestBody RefreshTokenDto refreshTokenDto){
        RefreshToken refreshTokenByUserName = refreshTokenService
                .findRefreshTokenByUserName(refreshTokenDto.getUserName());
        if (refreshTokenByUserName == null){
            throw new BadCredentialsException("No Refresh token found for userName "+ refreshTokenDto.getUserName());
        } else {
            if (refreshTokenService.isRefreshTokenExpired(refreshTokenByUserName)){
                throw new BadCredentialsException("Refresh token expired for userName "+ refreshTokenDto.getUserName());
            } else {
                if (refreshTokenDto.getRefreshToken().equalsIgnoreCase(refreshTokenByUserName.getToken())){
                    UserLoginDto userLoginDto = new UserLoginDto();
                    userLoginDto.setUserName(refreshTokenDto.getUserName());
                    userLoginDto.setUserType(
                            userService.extractUserById(
                                    refreshTokenByUserName.getUser().getId()
                            ).getUserType());
                    String jwtToken = jwtService.generateToken(userLoginDto);
                    return new ResponseEntity<>(new UserTokenDto(refreshTokenDto.getUserName(), jwtToken,
                            refreshTokenDto.getRefreshToken()), HttpStatus.OK);
                } else {
                    throw new BadCredentialsException("Refresh token not matching for userName "+ refreshTokenDto.getUserName());
                }
            }
        }
    }
}
