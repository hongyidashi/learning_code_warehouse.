package com.hl.algorithm;

/**
 * 描述:
 *
 * 给定一个正整数 N，找到并返回 N 的二进制表示中两个相邻 1 之间的最长距离。
 * 如果没有两个相邻的 1，返回 0 。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-17 20:53
 **/
public class Demo868 {

    public static void main(String[] args) {
        System.out.println(binaryGap(22));
    }

    /**
     * 相减法
     */
    public static int binaryGap(int N) {
        char[] chars = Integer.toBinaryString(N).toCharArray();

        int index = 0;
        int max = 0;

        for (int i = 0; i < chars.length; i++) {
            if ('1' == chars[i]) {
                max = Math.max(max, i - index);
                index = i;
            }
        }

        return max;
    }

    /**
     * 用数组存储索引
     */
    public static int binaryGap1(int N) {
        char[] chars = Integer.toBinaryString(N).toCharArray();
        int[] arr = new int[32];
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            if ('1' == chars[i]) {
                arr[index++] = i;
            }
        }
        int max = 0;
        for (int i = 0; i < index; i++) {
            max = Math.max(max, arr[i + 1] - arr[i]);
        }

        return max;
    }
}
