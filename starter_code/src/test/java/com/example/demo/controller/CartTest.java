package com.example.demo.controller;

import com.example.demo.Testutils;
import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class CartTest {


    private static CartController cartController = new CartController();
    private static CartRepository cartRepository = Mockito.mock(CartRepository.class);
    private static UserRepository userRepository = Mockito.mock(UserRepository.class);
    private static ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

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
        Testutils.injectObject(cartController, "cartRepository", cartRepository);
        Testutils.injectObject(cartController, "userRepository", userRepository);
        Testutils.injectObject(cartController, "itemRepository", itemRepository);
    }

    //Setup Objects before each Test
    @Before
    public void makeObjects() {

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
    public void addToCartHappyPath() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(user.getUsername());
        request.setQuantity(CART_ITEM_COUNT);
        request.setItemId(ITEM_ID);


        when(userRepository.findByUsername(USER_NAME)).thenReturn(user);
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.ofNullable(item));


        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(request);

        Assert.assertEquals(HttpStatus.OK, cartResponseEntity.getStatusCode());
        Assert.assertEquals( true,cartResponseEntity.getBody().getItems().contains(item));
        Assert.assertEquals( CART_ITEM_COUNT,cartResponseEntity.getBody().getItems().size());


    }

    @Test
    public void addToCartBadPath() {
        when(userRepository.findByUsername(USER_NAME)).thenReturn(user);
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.ofNullable(item));
        // Setting Wrong Username for provoking user not found Response
        user.setUsername(WRONG_USERNAME);
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(user.getUsername());
        request.setQuantity(CART_ITEM_COUNT);
        request.setItemId(ITEM_ID);

        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(request);

        //should return 404 because User is not found
        Assert.assertEquals(cartResponseEntity.getStatusCode(), HttpStatus.NOT_FOUND);


        //setting back to right Username and change item id to provoke Item Not Found

        user.setUsername(USER_NAME);

        request.setUsername(user.getUsername());

        //set wrong Item ID
        request.setItemId(0);

        ResponseEntity<Cart> cartResponseEntity1 = cartController.addTocart(request);
        //should return 404 because Items for Cart are not found
        Assert.assertEquals(HttpStatus.NOT_FOUND,cartResponseEntity1.getStatusCode());

    }

    @Test
    public void removeFromCartHappyPath() {
        when(userRepository.findByUsername(USER_NAME)).thenReturn(user);
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.ofNullable(item));


        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(user.getUsername());
        request.setQuantity(CART_ITEM_COUNT);
        request.setItemId(ITEM_ID);

        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(request);
        Assert.assertEquals(HttpStatus.OK,cartResponseEntity.getStatusCode());

    }

    @Test
    public void removeFromCartBadPath() {
        when(userRepository.findByUsername(USER_NAME)).thenReturn(user);
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.ofNullable(item));

        ModifyCartRequest request = new ModifyCartRequest();

        //Setting Wrong Username

        user.setUsername(WRONG_USERNAME);
        request.setUsername(user.getUsername());
        request.setQuantity(CART_ITEM_COUNT);
        request.setItemId(ITEM_ID);

        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(request);
        Assert.assertEquals(HttpStatus.NOT_FOUND,cartResponseEntity.getStatusCode());

        //Setting back to right Username and Wrong Item ID

        user.setUsername(USER_NAME);
        request.setUsername(user.getUsername());

        request.setItemId(0);

        ResponseEntity<Cart> cartResponseEntity1 = cartController.removeFromcart(request);
        Assert.assertEquals(HttpStatus.NOT_FOUND,cartResponseEntity.getStatusCode());

    }

}
