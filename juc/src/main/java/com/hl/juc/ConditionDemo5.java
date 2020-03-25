package com.hl.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData {

    private int number = 1;

    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() {
        printNumber(1, 2, 5, c1, c2);
    }

    public void print10() {
        printNumber(2, 3, 10, c2, c3);
    }

    public void print15() {
        printNumber(3, 1, 15, c3, c1);
    }

    public void printNumber(int flag, int nextFlag, int count, Condition c, Condition nextC) {
        lock.lock();
        try {
            while (number != flag) {
                c.await();
            }
            for (int i = 0; i < count; i++) {
                System.out.println(Thread.currentThread().getName() + "---->" + flag);
            }
            number = nextFlag;
            nextC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 实现 A打印5次，然后B打印10次，C再打印15次
 */
public class ConditionDemo5 {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

//        new Thread(shareData::print5,"A").start();
//        new Thread(shareData::print10,"B").start();
//        new Thread(shareData::print15,"C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print15();
            }
        }, "C").start();
    }
}
