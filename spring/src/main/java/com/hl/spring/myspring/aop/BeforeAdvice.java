package com.hl.spring.myspring.aop;

import java.lang.reflect.Method;

/**
 * @description: 前置通知
 * @author: panhongtong
 * @create: 2020-04-13 22:58
 **/
public class BeforeAdvice implements Advice {

    private Object bean;
    private MethodInvocation methodInvocation;

    public BeforeAdvice(Object bean, MethodInvocation methodInvocation) {
        this.bean = bean;
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodInvocation.method();
        return method.invoke(bean, args);
    }
}
