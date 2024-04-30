package com.example.demo.repo;


import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ItemRepoTest {


    @Autowired
    private ItemRepository itemRepository;


    //Item

    private Item item;

    private Item item2;

    private static final Long ITEM_ID = 1L;
    private static final Long ITEM2_ID = 2L;

    private static final Long WRONG_ITEM_ID = 13L;

    private static final String WRONG_ITEM_NAME = "UMPFGHUNFH";

    private static final String ITEM_NAME = "SuperDuper Item";

    private static final String ITEM_DESCRIPTION = "Super Premium Lootbox Item";

    private static final BigDecimal ITEM_PRICE = BigDecimal.valueOf(99.99);


    @Before
    public void createObjects(){
        item = new Item();
        item.setName(ITEM_NAME);
        item.setDescription(ITEM_DESCRIPTION);
        item.setPrice(ITEM_PRICE);
        item.setId(ITEM_ID);

        item = itemRepository.save(item);

        item2 = new Item();
        item2.setId(ITEM2_ID);
        item2.setName(ITEM_NAME);
        item2.setPrice(ITEM_PRICE);
        item2.setDescription(ITEM_DESCRIPTION);

        item2 = itemRepository.save(item2);

    }

    @Test
    public void getItemsByName(){
        List<Item> items = itemRepository.findByName(ITEM_NAME);
        Assert.assertEquals(2, items.size());
        Assert.assertTrue(items.containsAll(Arrays.asList(item,item2)));
    }

    @Test
    public void getItemsByID(){
        Optional<Item> testItem1 = itemRepository.findById(item.getId());
        Optional<Item> testItem2 = itemRepository.findById(item2.getId());

        Assert.assertTrue(testItem1.isPresent());
        Assert.assertTrue(testItem2.isPresent());

    }


}
