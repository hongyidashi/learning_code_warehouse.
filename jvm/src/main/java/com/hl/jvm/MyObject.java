package com.hl.jvm;

/**
 * @Auther: panhongtong
 * @Date: 2020/3/27 10:34
 * @Description: 验证类加载器
 */
public class MyObject {
    public static void main(String[] args) {
        System.out.println(Object.class.getClassLoader());
        System.out.println("-------------------------------");

        System.out.println(String.class.getClassLoader());
        System.out.println("-------------------------------");

        System.out.println(MyObject.class.getClassLoader());
        System.out.println(MyObject.class.getClassLoader().getParent());
        System.out.println(MyObject.class.getClassLoader().getParent().getParent());
    }
}
