package com.hl.basis.clazz;

/**
 * 描述:
 * 作者: panhongtong
 * 创建时间: 2020-09-11 07:53
 **/
public class SubClazz extends MyClazz {
    private String name;

    public static void main(String[] args) {
        MyClazz myClazz = null;
        //myClazz.test();
    }

}

class MyClazz {
    public String name;

    public void test() {
        System.out.println("啊");
    }
}