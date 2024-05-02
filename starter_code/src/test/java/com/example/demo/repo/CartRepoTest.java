package com.example.demo.repo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

@DataJpaTest
@DirtiesContext
public class CartRepoTest {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private Cart cart;

    //User
    private final Long USER_ID = 1L;
    private final String USER_NAME = "Testuser";
    private final String USER_PASSWORD = "password123";

    //Cart

    private final BigDecimal TOTAL_CART_PRICE = BigDecimal.valueOf(99.99);


    @BeforeEach
    public void createObjects(){
        user = new User();
        user.setUsername(USER_NAME);
        user.setId(USER_ID);
        user.setPassword(USER_PASSWORD);

        cart = new Cart();
        cart.setTotal(TOTAL_CART_PRICE);
        user.setCart(cart);
        user = userRepository.save(user);

    }


    @Test
    public void getCartFromUser(){
        Cart cart = cartRepository.findByUser(user);
        Assertions.assertEquals(TOTAL_CART_PRICE, cart.getTotal());
    }




}
