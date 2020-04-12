package com.hl.interview._01basis._05enum;

/**
 * @description: 枚举类的使用
 * @author: panhongtong
 * @create: 2020-04-12 23:00
 **/
public class PizzaDemo {
    public static void main(String[] args) {
        Pizza testPz = new Pizza();
        testPz.setStatus(Pizza.PizzaStatus.READY);

        testPz.printTimeToDeliver();
    }
}
