package com.example.demo;

import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class OrderTest {






    private static OrderController orderController = new OrderController();

    private static UserRepository userRepository = Mockito.mock(UserRepository.class);

    private static OrderRepository orderRepository = Mockito.mock(OrderRepository.class);


    private User user;
    private Item item;
    private Cart cart;

    //User
    private final Long USER_ID = 1L;
    private final String USER_NAME = "Testuser";
    private final String USER_PASSWORD = "password123";

    private final String WRONG_USERNAME = "MrWrong";

    //Item
    private final Long ITEM_ID = 1L;
    private final String ITEM_DESCRIPTION = "Very Expensive Demo Item to Conquer the World";
    private final String ITEM_NAME = "World Conquer AI Robot";

    private final BigDecimal ITEM_PRICE = BigDecimal.valueOf(599.99);

    //Cart
    private final Long CART_ID = 1L;
    private final int CART_ITEM_COUNT = 3;


    @BeforeClass
    public static void setUp() throws NoSuchFieldException, IllegalAccessException {
        Testutils.injectObject(orderController, "userRepository",userRepository);
        Testutils.injectObject(orderController, "orderRepository",orderRepository);
    }

    @Before
    public void createObjects(){

        //Cart
        cart = new Cart();
        cart.setId(CART_ID);


        // Setup User
        user = new User();
        user.setId(USER_ID);
        user.setUsername(USER_NAME);
        user.setPassword(USER_PASSWORD);

        user.setCart(cart);

        // Setup Item
        item = new Item();
        item.setId(ITEM_ID);
        item.setDescription(ITEM_DESCRIPTION);
        item.setName(ITEM_NAME);
        item.setPrice(ITEM_PRICE);

    }

    @Test
    public void submitOrderHappyPath(){

    }

    @Test
    public void submitOrderBadPath(){

    }

}
