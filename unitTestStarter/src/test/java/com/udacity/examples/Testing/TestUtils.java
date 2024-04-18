package com.udacity.examples.Testing;

import java.lang.reflect.Field;

public class TestUtils {


    public Object injectFieldsIntoObject(Object target, String fieldname, Object toInject){
        boolean wasPrivate = false;
        try {
            Field field = target.getClass().getDeclaredField(fieldname);
            if(!field.isAccessible()){
                wasPrivate = true;
                field.setAccessible(true);
            }

            field.set(target, toInject);

            if (wasPrivate){

            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }



}
