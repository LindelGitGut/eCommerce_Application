package com.example.demo;

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

public class CartTest {


    private static CartController cartController = new CartController();
    private static CartRepository cartRepository = Mockito.mock(CartRepository.class);
    private static UserRepository userRepository = Mockito.mock(UserRepository.class);
    private static ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

    private User user;
    private Item item;
    private Cart cart;

    //User
    private final Long USER_ID= 1L;
    private final String USER_NAME = "Testuser";
    private final String USER_PASSWORD = "password123";

    //Item

    private final Long ITEM_ID = 1L;
    private final String ITEM_DESCRIPTION = "Very Expensive Demo Item to Conquer the World";
    private final String ITEM_NAME = "World Conquer AI Robot";

    private final BigDecimal ITEM_PRICE = BigDecimal.valueOf(599.99);

    //Cart
    private final Long cartID = 1L;


    @BeforeClass
    public static void setUp() throws NoSuchFieldException, IllegalAccessException {
        Testutils.injectObject(cartController, "cartRepository", cartRepository);
        Testutils.injectObject(cartController, "userRepository", userRepository);
        Testutils.injectObject(cartController, "itemRepository", itemRepository);
    }

    //Setup Objects before each Test
    @Before
    public void makeObjects(){

        // Setup User
        user = new User();
        user.setId(USER_ID);
        user.setUsername(USER_NAME);
        user.setPassword(USER_PASSWORD);
        //NOT NEEDED
        //user.setCart();

        // Setup Item
        item = new Item();
        item.setId(ITEM_ID);
        item.setDescription(ITEM_DESCRIPTION);
        item.setName(ITEM_NAME);
        item.setPrice(ITEM_PRICE);



        cart = new Cart();


    }


    @Test
    public void addToCartHappyPath(){
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(user.getUsername());
        request.setQuantity(3);
        request.setItemId(ITEM_ID);

        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(request);

        Assert.assertEquals(cartResponseEntity.getStatusCode(), HttpStatus.OK);


    }

    @Test
    public void addToCartBadPath(){}

    @Test
    public void removeFromCartHappyPath(){}

    @Test
    public void removeFromCartBadPath(){}

}
