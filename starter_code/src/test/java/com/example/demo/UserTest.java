package com.example.demo;

import com.example.demo.Service.UserService;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class UserTest {

    static UserController userController;
    static UserRepository userRepository = Mockito.mock(UserRepository.class);
    static CartRepository cartRepository = Mockito.mock(CartRepository.class);

    static UserService userService = Mockito.mock(UserService.class);

    static BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);


    private User user;

    private final String USERNAME = "Testuser";
    private final String SHORT_PASSWORD = "short";
    private final String WRONG_PASSWORD = "wrongpassword";

    private final Long ID = 1L;

    @BeforeClass
    public static void setup() throws NoSuchFieldException, IllegalAccessException {
        userController = new UserController();
        Testutils.injectObject(userController, "userRepository", userRepository);
        Testutils.injectObject(userController, "cartRepository", cartRepository);
        Testutils.injectObject(userController, "userService", userService);
        Testutils.injectObject(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Before
    public void createTestUser(){
        user = new User();
        user.setUsername(USERNAME);
        user.setPassword("password");
        user.setId(ID);
    }


    @Test
    public void createUserHappyPath() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(user.getUsername());
        request.setPassword(user.getPassword());
        request.setConfirmPassword(user.getPassword());
        final ResponseEntity<User> responseEntity = userController.createUser(request);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void createUserBadPathConfirmPassword() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(user.getUsername());
        request.setPassword(user.getPassword());
        request.setConfirmPassword(WRONG_PASSWORD);
        final ResponseEntity<User> responseEntity = userController.createUser(request);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    //Password Length must be at least 7
    @Test
    public void createUserBadPathPasswordLength() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(user.getUsername());
        request.setPassword(SHORT_PASSWORD);
        request.setConfirmPassword(SHORT_PASSWORD);
        final ResponseEntity<User> response = userController.createUser(request);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    @Test
    public void findUserByIdHappyPath(){
        when(this.userRepository.findById(ID)).thenReturn(Optional.ofNullable(user));
        final ResponseEntity<User> response = userController.findById(user.getId());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void findUserByIdBadPath(){
        when(this.userRepository.findById(ID)).thenReturn(Optional.ofNullable(user));
        final ResponseEntity<User> response = userController.findById(user.getId()+1);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void findUserByNameHappyPath(){
        when(this.userRepository.findByUsername(USERNAME)).thenReturn(user);
        final ResponseEntity<User> response = userController.findByUserName(USERNAME);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void findUserByNameBadPath(){
        when(this.userRepository.findByUsername(USERNAME)).thenReturn(user);
        final ResponseEntity<User> response = userController.findByUserName(USERNAME+"Wrong");
        Assert.assertEquals(response.getStatusCode(),HttpStatus.NOT_FOUND);
    }




    //TODO check if should be deleted because delete is not necessary
 /*   @Test
    public void deleteUserByIdHappyPath(){
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("Alex");
        request.setPassword("password");
        request.setConfirmPassword("password");
        final ResponseEntity<User> responseEntity = userController.createUser(request);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        ResponseEntity<?> responseEntity1 = userController.deleteUser(ID);
        Assert.assertEquals(responseEntity1.getStatusCode(), HttpStatus.NO_CONTENT);

    }*/

  /*  @Test
    public void deleteUserByIdBadPath(){
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("Alex");
        request.setPassword("password");
        request.setConfirmPassword("password");
        final ResponseEntity<User> responseEntity = userController.createUser(request);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        ResponseEntity<?> responseEntity1 = userController.deleteUser(responseEntity.getBody().getId()+1);
        Assert.assertEquals(responseEntity1.getStatusCode(), HttpStatus.NOT_FOUND);
    }*/




}
