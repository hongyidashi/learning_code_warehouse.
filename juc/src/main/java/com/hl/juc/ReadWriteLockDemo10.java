package com.hl.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Mycache {

    private volatile Map<String, Object> map = new HashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "  正在写入数据：" + key);
            try {
                TimeUnit.MICROSECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "  写入数据完成");
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "  正在读取数据");
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "  读取数据完成：" + result);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

/**
 * @Auther: panhongtong
 * @Date: 2020/3/25 14:47
 * 读写锁
 */
public class ReadWriteLockDemo10 {
    public static void main(String[] args) {
        Mycache mycache = new Mycache();
        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(()-> mycache.put(tempInt + "", tempInt),tempInt + "").start();
        }
        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(()-> mycache.get(tempInt + ""),tempInt + "").start();
        }
    }
}
