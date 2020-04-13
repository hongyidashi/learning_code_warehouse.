package com.hl.spring.myspring.aop;

import java.lang.reflect.Proxy;

/**
 * @description: 简单AOP
 * @author: panhongtong
 * @create: 2020-04-13 23:03
 **/
public class SimpleAOP {
    public static Object getProxy(Object bean, Advice advice) {
        return Proxy.newProxyInstance(SimpleAOP.class.getClassLoader(),
                bean.getClass().getInterfaces(), advice);
    }
}
