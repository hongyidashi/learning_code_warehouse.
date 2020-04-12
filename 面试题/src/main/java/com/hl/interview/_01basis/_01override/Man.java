package com.hl.interview._01basis._01override;

/**
 * @description:
 * @author: panhongtong
 * @create: 2020-04-12 17:56
 **/
public class Man extends Persion {
    @Override
    public Man testOverride() {
        return new Man();
    }
}
