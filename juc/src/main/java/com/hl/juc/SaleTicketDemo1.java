package com.hl.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock使用
 */
class Ticket{
    private int number = 500;
    Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() +
                        "  卖出第" + (number--) + "张票。还剩下" + number + "张票");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SaleTicketDemo1 {
    public static void main(String[] args) {
        final Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                ticket.sale();
            }
        },"售票员1").start();
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                ticket.sale();
            }
        },"售票员2").start();
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                ticket.sale();
            }
        },"售票员3").start();
    }
}
