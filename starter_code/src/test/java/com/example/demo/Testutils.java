package com.example.demo;

import java.lang.reflect.Field;

public class Testutils {


    public static void injectObject (Object target, String fieldname, Object toInject) throws NoSuchFieldException, IllegalAccessException {
        boolean wasPrivate = false;
        Field field = target.getClass().getDeclaredField(fieldname);
        if (!field.isAccessible()) {
            field.setAccessible(true);
            wasPrivate = true;
            field.set(target, toInject);

            if (wasPrivate) {
                field.setAccessible(false);
            }
        }
    }
}
