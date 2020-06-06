package com.hl.interview._03concurrent._02atomic.ticketing;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: 票
 * 作者: panhongtong
 * 创建时间: 2020-06-05 22:35
 * 可通过原子类来解决买票问题
 **/
public class Ticket implements Runnable {

    static AtomicInteger num = new AtomicInteger(100);

    @Override
    public void run() {
        while (true) {
            if (num.get() > 0) {
                System.out.println("我买到了票！" + num.decrementAndGet());
            } else {
                System.out.println("票没了QAQ" + num.get());
                break;
            }
        }
    }
}
