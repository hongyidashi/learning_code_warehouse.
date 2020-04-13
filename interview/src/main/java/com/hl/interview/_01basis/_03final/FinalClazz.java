package com.hl.interview._01basis._03final;

import lombok.Data;

/**
 * @description: 测试final相关
 * @author: panhongtong
 * @create: 2020-04-12 21:27
 **/
@Data
public final class FinalClazz {

    private final String name;

    public void method() {
        System.out.println("方法");
    }
}
