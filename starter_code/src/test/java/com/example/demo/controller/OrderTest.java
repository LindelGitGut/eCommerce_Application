package com.example.demo.controller;

import com.example.demo.Testutils;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderTest {

    private static OrderController orderController = new OrderController();

    private static UserRepository userRepository = Mockito.mock(UserRepository.class);

    private static OrderRepository orderRepository = Mockito.mock(OrderRepository.class);


    private User user;
    private Item item;

    private Item item2;

    private Item item3;
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
    private final BigDecimal ITEM_PRICE2 = BigDecimal.valueOf(99.99);
    private final BigDecimal ITEM_PRICE3 = BigDecimal.valueOf(10.99);;

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

        //add item to Cart

        cart.addItem(item);

        //create 2nd ITem

        item2 = new Item();
        item2.setPrice(ITEM_PRICE2);
        item2.setName("GlassFishTank");
        item2.setDescription("Glowing Glassfish Tank");
        item2.setId(2L);

        item3 = new Item();
        item3.setPrice(ITEM_PRICE3);
        item3.setName("Glowing Ball");
        item3.setDescription("Glowing Ball");
        item3.setId(3L);

        // add Item to User Cart

       cart.addItem(item2);

        //add Cart to User
        user.setCart(cart);

    }

    @Test
    public void submitOrderHappyPath(){
        when(userRepository.findByUsername(USER_NAME)).thenReturn(this.user);
        ResponseEntity<UserOrder> responseEntity = orderController.submit(USER_NAME);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //check if Totalprice is right
        Assert.assertEquals(ITEM_PRICE.add(ITEM_PRICE2), responseEntity.getBody().getTotal());
        //check if Item Count is right
        Assert.assertEquals(2,responseEntity.getBody().getItems().size());
        Assert.assertEquals(user, responseEntity.getBody().getUser());

    }

    @Test
    public void submitOrderBadPath(){
        when(userRepository.findByUsername(USER_NAME)).thenReturn(this.user);
        ResponseEntity<UserOrder> responseEntity = orderController.submit(WRONG_USERNAME);

        Assert.assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());

    }

    @Test
    public void getOrdersByUserHappyPath(){


        UserOrder userOrder = UserOrder.createFromCart(user.getCart());

        // make 2nd Order with 3rd item

        user.getCart().removeItem(item);
        user.getCart().removeItem(item2);

        user.getCart().addItem(item3);

        UserOrder userorder2 = UserOrder.createFromCart(user.getCart());

        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);
        orders.add(userorder2);

        when(userRepository.findByUsername(USER_NAME)).thenReturn(this.user);
        when(orderRepository.findByUser(user)).thenReturn(orders);


        ResponseEntity<List<UserOrder>> ordersForUser = orderController.getOrdersForUser(user.getUsername());

        Assert.assertEquals( HttpStatus.OK, ordersForUser.getStatusCode());

        //TODO check if Values in List are correct

    }

    @Test
    public void getOrdersByUserBadPath() {

        //Username not found
        when(userRepository.findByUsername(USER_NAME)).thenReturn(this.user);
        when(orderRepository.findByUser(user)).thenReturn(Arrays.asList(UserOrder.createFromCart(user.getCart())));

        ResponseEntity<List<UserOrder>> ordersForUser = orderController.getOrdersForUser(WRONG_USERNAME);

        Assert.assertEquals(HttpStatus.NOT_FOUND, ordersForUser.getStatusCode());

    }
}
