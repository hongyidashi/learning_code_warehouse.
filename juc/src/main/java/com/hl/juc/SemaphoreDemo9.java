package com.hl.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: panhongtong
 * @Date: 2020/3/25 13:55
 * Semaphore：抢车位
 * 用于并发控制和资源互斥
 */
public class SemaphoreDemo9 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "  获得到了资源");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + "  释放了资源");
                    semaphore.release();
                }
            }, i + "").start();
        }
    }
}
