package com.hl.spring.myspring.aop;

/**
 * @description: 测试业务实现类
 * @author: panhongtong
 * @create: 2020-04-13 23:05
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHelloWorld() {
        System.out.println("手写简单AOP切面~~~");
    }
}
