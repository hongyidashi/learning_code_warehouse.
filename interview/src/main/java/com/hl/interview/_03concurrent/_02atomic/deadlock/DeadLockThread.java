package com.hl.interview._03concurrent._02atomic.deadlock;

/**
 * 描述: 线程
 * 作者: panhongtong
 * 创建时间: 2020-06-06 14:31
 **/
public class DeadLockThread implements Runnable {

    boolean flag;

    public DeadLockThread(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag) {
            while (true) {
                synchronized (MyResources.resources1) {
                    System.out.println(Thread.currentThread().getName() + "使用资源1中...");
                    synchronized (MyResources.resources2) {
                        System.out.println(Thread.currentThread().getName() + "使用资源2中...");
                    }
                }
            }
        } else {
            while (true) {
                synchronized (MyResources.resources2) {
                    System.out.println(Thread.currentThread().getName() + "使用资源2中...");
                    synchronized (MyResources.resources1) {
                        System.out.println(Thread.currentThread().getName() + "使用资源1中...");
                    }
                }
            }
        }
    }
}
