package com.udacity.examples.Testing;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelperTest {


    Usercontroller usercontroller;




    @BeforeClass
    public static void setup(){


    }

    @Test
    public void doSomeThing(){
        Assert.assertEquals("","");
    }

    @Test
    public void getMergedListTest(){
        List<String> stringList = Arrays.asList("Alex", "Bea");
        String expected = "Alex, Bea";
        String mergedList = Helper.getMergedList(stringList);
        Assert.assertEquals(expected,mergedList);

    }

	
}
