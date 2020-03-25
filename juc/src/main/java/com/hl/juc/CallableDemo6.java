package com.hl.juc;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class Mythread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("好的猫咪，没问题猫咪");
        TimeUnit.SECONDS.sleep(4);
        int result = new Random().nextInt(10);
        System.out.println(result);
        return result;
    }
}

/**
 * Callable<T>的使用
 */
public class CallableDemo6 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask(new Mythread());
        new Thread(futureTask, "A").start();
        //即使start了两次，也只会去调用一次，会将结果放入缓存中
        new Thread(futureTask, "B").start();

        System.out.println("main execute here...");

        Integer result = futureTask.get();
        System.out.println(result);
    }
}
