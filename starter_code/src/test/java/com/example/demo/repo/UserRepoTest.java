package com.example.demo.repo;


import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext
public class UserRepoTest {

    @Autowired
    private UserRepository userRepository;


    //user
    private User user;

    private final String USERNAME = "Testuser";

    private final String PASSWORD = "password";

    private final Long USER_ID = 1L;


    @BeforeEach
    public void createObjects(){
        user = new User();
        user.setId(USER_ID);
        user.setPassword(PASSWORD);
        user.setUsername(USERNAME);

        user = userRepository.save(user);
    }


    @Test
    public void getUserByUserName(){
        User searchUser = userRepository.findByUsername(USERNAME);
        Assertions.assertTrue(searchUser.equals(user));
    }

}
