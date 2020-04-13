package com.hl.interview._01basis._06reflection;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @description: 测试反射
 * @author: panhongtong
 * @create: 2020-04-12 23:14￿￿￿￿￿￿
 **/
public class Reflection {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> targetClass = Class.forName("com.hl.interview._01basis._06reflection.ReflectionObject");

        //实例化该类
        ReflectionObject targetObejct = (ReflectionObject) targetClass.newInstance();

        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
    }
}
