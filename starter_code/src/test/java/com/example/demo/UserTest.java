package com.example.demo;

import com.example.demo.Service.UserService;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserTest {

    static UserController userController;
    static UserRepository userRepository = Mockito.mock(UserRepository.class);
    static CartRepository cartRepository = Mockito.mock(CartRepository.class);

    static UserService userService = Mockito.mock(UserService.class);

    static BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

    @BeforeClass
    public static void setup() throws NoSuchFieldException, IllegalAccessException {
        userController = new UserController();
        Testutils.injectObject(userController, "userRepository", userRepository);
        Testutils.injectObject(userController, "cartRepository", cartRepository);
        Testutils.injectObject(userController, "userService", userService);
        Testutils.injectObject(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }


    @Test
    public void createUserHappyPath() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("Alex");
        request.setPassword("password");
        request.setConfirmPassword("password");
        final ResponseEntity<User> responseEntity = userController.createUser(request);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void createUserBadPathConfirmPassword() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("BadUser");
        request.setPassword("rightpassword");
        request.setConfirmPassword("wrongpassword");
        final ResponseEntity<User> responseEntity = userController.createUser(request);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    //Password Length must be at least 7
    @Test
    public void createUserBadPathPasswordLength() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("BadUser2");
        request.setPassword("short");
        request.setConfirmPassword("short");
        final ResponseEntity<User> response = userController.createUser(request);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


}
