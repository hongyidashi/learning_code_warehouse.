package com.hl.spring.myspring.ioc;

import com.hl.spring.bean.Boy;
import com.hl.spring.bean.Girl;
import org.junit.Test;

/**
 * @description: 测试IOC
 * @author: panhongtong
 * @create: 2020-04-13 21:14
 **/
public class IOCTest {

    @Test
    public void testIOCBean() throws Exception {
        String location = SimpleIOC.class.getClassLoader().getResource("spring-test.xml").getFile();
        SimpleIOC bf = new SimpleIOC(location);
        Girl girl = (Girl) bf.getBean("girl");
        Boy boy = (Boy) bf.getBean("boy");
        System.out.println(girl);
        System.out.println(boy);
    }
}
