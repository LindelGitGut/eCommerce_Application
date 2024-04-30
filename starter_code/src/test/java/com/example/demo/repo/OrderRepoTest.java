package com.example.demo.repo;


import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.Order;

@DataJpaTest
@RunWith(SpringRunner.class)
public class OrderRepoTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    //User
    private final Long USER_ID = 1L;
    private final String USER_NAME = "Testuser";
    private final String USER_PASSWORD = "password123";

    //Order

    private User user;


    private Order order;


    @Before
    public  void CreateObjects(){

        user = new User();
        user.setId(USER_ID);
        user.setUsername(USER_NAME);
        user.setPassword(USER_PASSWORD);



    }


    @Test
    public void findOrdersByUser(){



    }


}
