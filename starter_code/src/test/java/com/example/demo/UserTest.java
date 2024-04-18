package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

public class UserTest {

      UserController userController;
     UserRepository userRepository = Mockito.mock(UserRepository.class);
     CartRepository cartRepository = Mockito.mock(CartRepository.class);

    @BeforeClass
    public  void setup() throws NoSuchFieldException, IllegalAccessException {
      userController = new UserController();
      Testutils.injectObject(userController, "userRepository", userRepository);
        Testutils.injectObject(userController, "cartRepository", cartRepository);
    }


    @Test
    public static void createUserHappyPath(){

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("Alex");
        request.setPassword("password");
        request.setConfirmPassword("password");
        final ResponseEntity<User> responseEntity = userController.createUser(request);
    }



}
