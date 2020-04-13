package com.hl.interview._01basis._01override;

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

}
