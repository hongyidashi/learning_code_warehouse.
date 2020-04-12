package com.hl.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description: 实现自旋锁
 * @author: panhongtong
 * @create: 2020-04-12 10:05
 **/
public class SpinLockDemo13 {

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println("当前线程为：" + thread);
        while (!atomicReference.compareAndSet(null,thread)) {

        }
    }

    public void myUnlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread + "释放了锁");
    }

    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo13 spinLockDemo13 = new SpinLockDemo13();

        new Thread(() -> {
            spinLockDemo13.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo13.myUnlock();
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            spinLockDemo13.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo13.myUnlock();
        }).start();
    }
}
