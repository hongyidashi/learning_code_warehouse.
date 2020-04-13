package com.hl.interview._01basis._06reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @description: 测试反射
 * @author: panhongtong
 * @create: 2020-04-12 23:14￿￿￿￿￿￿
 **/
public class Reflection {
    public static void main(String[] args) throws Exception {
        Class<?> targetClass = Class.forName("com.hl.interview._01basis._06reflection.ReflectionObject");

        //实例化该类
        ReflectionObject targetObejct = (ReflectionObject) targetClass.newInstance();

        //获取所有方法
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        System.out.println("---------------------------------------------");

        //获取指定方法并调用
        Method publicMethod = targetClass.getDeclaredMethod("publicMethod", String.class);
        publicMethod.invoke(targetObejct, "断腿少女");
        System.out.println("---------------------------------------------");

        //获取指定字段并设值
        Field field1 = targetClass.getDeclaredField("integer");
        Field field2 = targetClass.getDeclaredField("value");
        field1.set(targetObejct, 9);
        //取消安全检查，如果访问的是private字段必须设值，否则出现异常
        field2.setAccessible(true);
        field2.set(targetObejct,"断腿了吧");
        System.out.println(targetObejct);
        System.out.println("---------------------------------------------");

        //调用private方法
        Method privateMethod = targetClass.getDeclaredMethod("privateMethod");
        //为了调用private方法取消安全检查
        privateMethod.setAccessible(true);
        privateMethod.invoke(targetObejct);
        System.out.println("---------------------------------------------");

        //获得执行当前线程的类名
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        Class<?> aClass = Class.forName(className);
        //获得包名
        Package aPackage = aClass.getPackage();
        System.out.println(aPackage);
    }
}
