package com.hl.juf;

import java.util.concurrent.CompletableFuture;

/**
 * @Auther: panhongtong
 * @Date: 2020/3/27 09:19
 * @Description:
 */
public class CompletableFutureDemo3 {
    public static void main(String[] args) throws Exception {
        //均可使用链式编程
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> System.out.println(Thread.currentThread().getName() + "---->无返回值异步回调"));
        voidCompletableFuture.get();

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "---->有返回值异步回调");
            //int i = 10 / 0;
            return "OK";
        });
        //System.out.println(stringCompletableFuture.get());
        String resultString = stringCompletableFuture.whenComplete((p1, p2) -> {
            //参数p1是正常返回值，参数p2是异常
            System.out.println("参数p1:" + p1);
            System.out.println("参数p2:" + p2);
        }).exceptionally(f -> {   //如果发生异常会执行这个
            System.out.println("发生异常：" + f.getMessage());
            return "exception happened";
        }).get();
        System.out.println(resultString);

//        ForkJoinPool.commonPool-worker-9---->有返回值异步回调
//        参数p1:null
//        参数p2:java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
//        发生异常：java.lang.ArithmeticException: / by zero
//        exception happened

    }
}
