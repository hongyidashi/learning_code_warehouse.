package com.hl.interview._01basis._01override_overload;

/**
 * @description:
 * @author: panhongtong
 * @create: 2020-04-12 17:56
 **/
public class Man extends Persion {
    @Override
    public Man testOverride() {
        return new Man();
    }

    // private/final/static 修饰的方法能够被再次声明
    public static void testStaticMethod() {
        System.out.println("啊？");
    }

    private void testPrivateMethod() {
        System.out.println("私有方法");
    }
}
