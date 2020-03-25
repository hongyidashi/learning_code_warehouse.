package com.hl.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Aircondition {

    private int number;

    //可重用、非公平、递归锁
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * 使用lock进行同步
     */
    public void increment() throws Exception {
        lock.lock();
        try {
            while (number != 0) { condition.await(); }
            number++;
            System.out.println(Thread.currentThread().getName() + "  number = " + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void decrement() throws Exception {
        lock.lock();
        try {
            while (number == 0) { condition.await(); }
            number--;
            System.out.println(Thread.currentThread().getName() + "  number = " + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 多线程判断要用while，不能用if
     * 否则会出现虚假唤醒，会出现程序一直不结束、结果不对的情况
     * 使用synchronized同步
     */
    /*public synchronized void increment() throws InterruptedException {
        while (number != 0) { this.wait(); }
//        if (number != 0) { this.wait(); }
        number++;
        System.out.println(Thread.currentThread().getName() + "  number = " + number);
        this.notifyAll();
    }
    public synchronized void decrement() throws InterruptedException {
        while (number == 0) { this.wait(); }
//        if (number == 0) { this.wait(); }
        number--;
        System.out.println(Thread.currentThread().getName() + "  number = " + number);
        this.notifyAll();
    }*/

}

public class ProductConsumerDemo4 {
    public static void main(String[] args) {
        ProductConsumerDemo4 demo4 = new ProductConsumerDemo4();
        demo4.test1();
    }

    public void test1() {
        Aircondition aircondition = new Aircondition();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}
