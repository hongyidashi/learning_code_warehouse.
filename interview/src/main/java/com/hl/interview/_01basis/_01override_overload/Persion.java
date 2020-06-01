package com.hl.interview._01basis._01override_overload;

import lombok.Data;

/**
 * @description:
 * @author: panhongtong
 * @create: 2020-04-12 17:55
 **/
@Data
public class Persion {

    private String name;
    private Integer age;

    public Persion testOverride() {
        return new Persion();
    }

    public static void testStaticMethod() {
        System.out.println("fuck");
    }

    private void testPrivateMethod() {
        System.out.println("私有方法");
    }

}
