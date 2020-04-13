package com.hl.spring.bean;

import lombok.Data;

/**
 * @description: 测试bean
 * @author: panhongtong
 * @create: 2020-04-13 21:06
 **/
@Data
public class Girl {

    private String id;
    private String name;
    private Boy boyfriend;

}
