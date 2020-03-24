package com.hl.juc;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotSafeDemo2 {
    public static void main(String[] args) {
        //List集合出现并发错误
        //List<String> list = new ArrayList();
        //解决方法一，将集合变为线程安全
        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        //解决方法二使用线程安全类
        //List<String> list = new Vector<>();
        //解决方法三，使用JUC下的线程安全集合
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "---->" + list);
            },String.valueOf(i)).start();
        }
    }
}
