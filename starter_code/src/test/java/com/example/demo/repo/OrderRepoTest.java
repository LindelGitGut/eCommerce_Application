package com.example.demo.repo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@DirtiesContext
public class OrderRepoTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    //Item

    private static final Long ITEM_ID = 3L;

    private static final String ITEM_NAME = "SuperDuper Item";

    private static final String ITEM_DESCRIPTION = "Super Premium Lootbox Item";

    private static final BigDecimal ITEM_PRICE = BigDecimal.valueOf(99.99);


    //User
    private final Long USER_ID = 1L;
    private final String USER_NAME = "Testuser";
    private final String USER_PASSWORD = "password123";


    private Cart cart;
    private Item item;

    private Item item2;

    private Item item3;
    private User user;

    private UserOrder order;


    @BeforeEach
    public void CreateObjects() {
        user = new User();
        user.setId(USER_ID);
        user.setUsername(USER_NAME);
        user.setPassword(USER_PASSWORD);

        item = new Item();
        item.setId(ITEM_ID);
        item.setName(ITEM_NAME);
        item.setPrice(ITEM_PRICE);
        item.setDescription(ITEM_DESCRIPTION);


        //saving item with ID 3
        item = itemRepository.save(item);


        // retriving other Items from Data.sql
        item2 = itemRepository.findById(1L).get();

        item3 = itemRepository.findById(2L).get();


        cart = new Cart();
        cart.addItem(item);
        cart.addItem(item2);
        cart.addItem(item3);

        user.setCart(cart);

        user = userRepository.save(user);
        order = UserOrder.createFromCart(user.getCart());
        order.setUser(user);
        order = orderRepository.save(order);
    }


    @Test
    public void findOrdersByUser() {
        List<UserOrder> orders = orderRepository.findByUser(user);
        Assertions.assertEquals(1, orders.size());
        Assertions.assertEquals(3,orders.get(0).getItems().size());
        Assertions.assertTrue(orders.get(0).getItems().containsAll(Arrays.asList(item,item2,item3)));
        Assertions.assertTrue(orders.get(0).getItems().get(0).equals(item));
        Assertions.assertTrue(orders.get(0).getItems().get(1).equals(item2));
        Assertions.assertTrue(orders.get(0).getItems().get(2).equals(item3));
    }


}
