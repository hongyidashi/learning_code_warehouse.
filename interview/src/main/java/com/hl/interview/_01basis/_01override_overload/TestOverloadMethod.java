package com.hl.interview._01basis._01override_overload;

/**
 * 描述：测试重载
 * 作者: panhongtong
 * 创建时间: 2020-05-30 21:16
 **/
public class TestOverloadMethod {

    public static void main(String[] args) {
        testMethod();
        testMethod("我擦");
    }

    //static方法可以被重载
    public static void testMethod() {
        System.out.println("普通static方法");
    }

    public static void testMethod(String str) {
        System.out.println("重载static方法" + str);
    }
}
