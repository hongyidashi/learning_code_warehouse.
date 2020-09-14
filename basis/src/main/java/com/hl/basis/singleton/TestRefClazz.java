package com.hl.basis.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * 描述:
 * 作者: panhongtong
 * 创建时间: 2020-09-09 16:09
 **/
public class TestRefClazz {
    public static void main(String[] args) throws Exception {
        // com.hl.basis.singleton.MySingleton
        Class<?> clazz = Class.forName("com.hl.basis.singleton.MySingleton");
        System.out.println(clazz);
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        MySingleton o = (MySingleton) constructor.newInstance();

        System.out.println(o.name);
        //Field name = clazz.getField("name");
        //name.setAccessible(true);
        //Object o1 = name.get(clazz);
        System.out.println(o);
        //System.out.println(o1);
        //System.out.println(name);
    }
}
