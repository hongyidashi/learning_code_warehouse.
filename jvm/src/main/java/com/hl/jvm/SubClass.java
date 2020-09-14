package com.hl.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 作者: panhongtong
 * 创建时间: 2020-09-04 14:35
 **/
public class SubClass extends SuperClass {

    int j = 30;

    public SubClass() {
        print();
        j = 40;
    }

    @Override
    public void print() {
        System.out.println(j);
    }

    {
        j = 50;
    }

    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        System.out.println(subClass.j);
        System.out.println(subClass.i);
    }
}

class SuperClass {

    static int i = 10;

    static {
        i = 999;
        System.out.println("傻子啊");
    }

    public SuperClass() {
        print();
        i = 20;
    }

    public void print() {
        System.out.println(i);
    }
}
