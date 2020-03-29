package com.hl.jvm;

/**
 * @author panhongtong
 * @date 2020/3/29
 * @description JVM参数
 */
public class JVMParams {
    public static void main(String[] args) {
        //Runtime运行时数据区的抽象
        System.out.println("处理器个数：" + Runtime.getRuntime().availableProcessors());
        //Java虚拟机试图使用的最大内存，默认 1/4 字节
        long maxMemory = Runtime.getRuntime().maxMemory();
        //Java虚拟机的初始分配内存 默认 1/64 字节
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("最大内存 -Xmx：" + maxMemory / (double) 1024 / 1024 + "MB");
        System.out.println("初始分配内存 -Xms：" + totalMemory / (double) 1024 / 1024 + "MB");

        //VM参数：-Xmx1024m -Xms1024m -XX:+PrintGCDetails
    }
}
