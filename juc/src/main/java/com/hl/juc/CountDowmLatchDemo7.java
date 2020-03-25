package com.hl.juc;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch的使用
 * CountDownLatch的计数必须为零才能往下执行，否则会被await()阻塞
 * 倒计时
 */
public class CountDowmLatchDemo7 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "   打卡");
                countDownLatch.countDown();
            }, i + "").start();
        }
        countDownLatch.await();
        System.out.println("~~~~~~~~~~~~~~~~分割~~~~~~~~~~~~~~~~");
    }

    private static void test1() {
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "   打卡");
            }, i + "").start();
        }
        System.out.println("~~~~~~~~~~~~~~~~分割~~~~~~~~~~~~~~~~");
    }

}
