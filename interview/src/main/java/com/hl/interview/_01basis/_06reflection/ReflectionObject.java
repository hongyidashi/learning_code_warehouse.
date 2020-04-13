package com.hl.interview._01basis._06reflection;

import lombok.Data;

/**
 * @description: 反射目标类
 * @author: panhongtong
 * @create: 2020-04-12 23:11
 **/
@Data
public class ReflectionObject {

    private String value;

    Integer integer;

    public void publicMethod(String s) {
        System.out.println("this public method --->" + s);
    }

    private void privateMethod() {
        System.out.println("this private method --->" + value);
    }
}
