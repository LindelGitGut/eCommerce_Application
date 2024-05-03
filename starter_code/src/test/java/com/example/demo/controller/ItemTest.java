package com.example.demo.controller;

import com.example.demo.Testutils;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class ItemTest {


    private static final ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

    private static final ItemController itemController = new ItemController();

    //Item 1
    private Item item;
    private static final Long ITEM_ID = 1L;

    private static final Long WRONG_ITEM_ID = 13L;

    private static final String WRONG_ITEM_NAME = "UMPFGHUNFH";

    private static final String ITEM_NAME = "SuperDuper Item";

    private static final String ITEM_DESCRIPTION = "Super Premium Lootbox Item";

    private static final BigDecimal ITEM_PRICE = BigDecimal.valueOf(99.99);

    //Item2

    private Item item2;
    private static final Long ITEM2_ID = 2L;

    private static final String ITEM2_NAME = "SuperDuper Item";

    private static final String ITEM2_DESCRIPTION = "Meh Lootbox Item";

    private static final BigDecimal ITEM2_PRICE = BigDecimal.valueOf(5.99);




    @BeforeClass
    public static void setUp() throws NoSuchFieldException, IllegalAccessException {
        Testutils.injectObject(itemController, "itemRepository", itemRepository);

    }

    @Before
    public void createObject(){
        item = new Item();
        item.setId(ITEM_ID);
        item.setName(ITEM_NAME);
        item.setDescription(ITEM_DESCRIPTION);
        item.setPrice(ITEM_PRICE);

        item2 = new Item();
        item2.setId(ITEM2_ID);
        item2.setName(ITEM2_NAME);
        item2.setDescription(ITEM2_DESCRIPTION);
        item2.setPrice(ITEM2_PRICE);
    }


    @Test
    public void getItemsByIDHappyPath(){
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.ofNullable(item));

        ResponseEntity<Item> itemById = itemController.getItemById(ITEM_ID);
        Assert.assertEquals(HttpStatus.OK, itemById.getStatusCode());
        Assert.assertEquals(item,itemById.getBody());
    }


    @Test
    public void getItemsByIDBadPath(){
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.ofNullable(item));
        ResponseEntity<Item> itemById = itemController.getItemById(WRONG_ITEM_ID);
        Assert.assertEquals(HttpStatus.NOT_FOUND, itemById.getStatusCode());
        Assert.assertNull(itemById.getBody());
    }

    @Test
    public void getItemsByNameHappyPath(){
        when(itemRepository.findByName(ITEM_NAME)).thenReturn(Arrays.asList(item,item2));

        ResponseEntity<List<Item>> itemsByName = itemController.getItemsByName(ITEM_NAME);

        Assert.assertEquals(HttpStatus.OK, itemsByName.getStatusCode());
        Assert.assertEquals(2, itemsByName.getBody().size());
        Assert.assertTrue(itemsByName.getBody().containsAll(Arrays.asList(item, item2)));

    }

    @Test
    public void getItemsByNameBadPath(){
        when(itemRepository.findByName(ITEM_NAME)).thenReturn(Arrays.asList(item,item2));

        ResponseEntity<List<Item>> itemsByName = itemController.getItemsByName(WRONG_ITEM_NAME);

        Assert.assertEquals(HttpStatus.NOT_FOUND, itemsByName.getStatusCode());
        Assert.assertNull(itemsByName.getBody());

    }

    @Test
    public void getAllItems(){
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item,item2));
        ResponseEntity<List<Item>> items = itemController.getItems();

        Assert.assertEquals(HttpStatus.OK, items.getStatusCode());
        Assert.assertEquals(2, items.getBody().size());
    }

}
