package com.example.demo.repo;


import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepoTest {

    @Autowired
    private UserRepository userRepository;


    //user
    private User user;

    private final String USERNAME = "Testuser";

    private final String PASSWORD = "password";

    private final Long USER_ID = 1L;


    @Before
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
        Assert.assertTrue(searchUser.equals(user));
    }

}
