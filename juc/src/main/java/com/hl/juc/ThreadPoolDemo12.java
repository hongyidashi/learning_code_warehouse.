package com.hl.juc;

import java.util.concurrent.*;

/**
 * @Auther: panhongtong
 * @Date: 2020/3/26 10:26
 */
public class ThreadPoolDemo12 {
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),   //可不设置，默认值为Integer.MAX_VALUE，建议设置
                Executors.defaultThreadFactory(),
                //new ThreadPoolExecutor.AbortPolicy())   //默认策略：直接拒绝并报异常
                //new ThreadPoolExecutor.CallerRunsPolicy())   //回退策略：让该任务回退到调用者
                //new ThreadPoolExecutor.DiscardPolicy())   //放弃策略1：放弃任务，不报错
                new ThreadPoolExecutor.DiscardOldestPolicy())   //放弃策略2：放弃等待最久的任务，不报错
                ;
        executeT(threadPool);
    }

    private static void executeT(ExecutorService threadPool) {
        try {
            for (int i = 0; i < 10; i++) {
                int tempInt = i;
                threadPool.execute(new Thread(() -> {
                    System.out.println(tempInt + "开始占用线程--->" + Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(tempInt + "释放线程--->" + Thread.currentThread().getName());
                }, i + ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    //API生成线程池（不推荐使用）
    private static void ExecutorsAPI() {
        //固定线程池
        //执行长期任务性能好，创建一个线程池，一池有N个固定线程，有固定的线程数的线程池
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //自动扩容线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();
        executeT(threadPool);
    }
}
