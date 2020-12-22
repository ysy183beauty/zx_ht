package com.szbase.credit.controller;

import java.lang.reflect.Field;

/**
 * Created by dlms on 2017/2/13.
 */
public class Test {
    public static void main(String[] args){
        int  a=10;
        int b=10;
       method(a,b);
        System.out.println(a);
        System.out.println(b);
    }

    public static void method(int aInt,int b){
        Class clasz = Integer.class.getDeclaredClasses()[0];
        try {
            Field field=clasz.getDeclaredField("cache");
            field.setAccessible(true);
            Integer[] array =(Integer[])field.get(clasz);
            array[138]=100;
            array[148]=200;

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
