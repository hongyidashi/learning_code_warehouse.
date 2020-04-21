package com.hl.sort;

import java.util.Arrays;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/21 13:48
 * @Description: 希尔排序
 */
public class ShellSort04 {
    public static void main(String[] args) {
        int[] arr = {8, 9, 7, 1, 2, 3, 5, 4, 6, 0};
        shellSort1(arr);
    }

    public static void shellSort1(int[] arr) {
        int temp;
        int flag = arr.length - arr.length / 2;
        while (flag > 0) {
            for (int i = flag; i < arr.length; i++) {
                for (int j = i - flag; j >= 0; j-= flag) {
                    if (arr[j] > arr[j + flag]) {
                        temp = arr[j];
                        arr[j] = arr[j + flag];
                        arr[j + flag] = temp;
                    }
                }
            }
            flag = flag / 2;
        }
        System.out.println(Arrays.toString(arr));
    }
}
