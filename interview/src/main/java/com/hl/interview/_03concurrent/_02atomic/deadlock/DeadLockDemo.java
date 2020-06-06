package com.hl.interview._03concurrent._02atomic.deadlock;

/**
 * 描述: 死锁演示
 * 作者: panhongtong
 * 创建时间: 2020-06-06 14:30
 **/
public class DeadLockDemo {
    public static void main(String[] args) {
        new Thread(new DeadLockThread(true)).start();
        new Thread(new DeadLockThread(false)).start();
    }
}
