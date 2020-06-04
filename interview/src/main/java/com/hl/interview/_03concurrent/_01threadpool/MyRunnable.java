package com.hl.interview._03concurrent._01threadpool;

import lombok.ToString;

import java.time.LocalTime;

/**
 * 描述: 线程
 * 作者: panhongtong
 * 创建时间: 2020-06-03 22:12
 **/
@ToString
public class MyRunnable implements Runnable {

    private String command;


    public MyRunnable(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Time = " + LocalTime.now());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " End. Time = " + LocalTime.now());
    }

    private void processCommand() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
