package com.hl.interview._03concurrent._01threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 测试使用线程池
 * 作者: panhongtong
 * 创建时间: 2020-06-03 22:16
 **/
public class ThreadPoolExecutorDemo {

    // 核心线程数
    private static final int CORE_POOL_SIZE = 5;
    // 线程池最大容量
    private static final int MAX_POOL_SIZE = 10;
    // 队列长度
    private static final int QUEUE_CAPACITY = 100;
    // 空闲线程存活时间
    private static final Long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {
            Runnable work = new MyRunnable(i + "");
            executor.execute(work);
        }

        // 终止线程池，如果还有线程在活跃就不会被终止
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("所有线程执行完毕");
    }
}
