package com.hl.interview._01basis._01override_overload;

/**
 * @description: 测试重写
 * @author: panhongtong
 * @create: 2020-04-12 17:55
 **/
public class OverrideTest {
    public static void main(String[] args) {
        Man man = new Man();
        System.out.println(man.testOverride().getClass());
        Man.testStaticMethod();
        man.testStaticMethod();
    }
}
