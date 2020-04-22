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
        //shellSort1(arr);
        shellSort2(arr);
    }

    /**
     * 交换法
     */
    public static void shellSort1(int[] arr) {
        int temp;
        int flag = arr.length - arr.length / 2;
        while (flag > 0) {
            for (int i = flag; i < arr.length; i++) {
                for (int j = i - flag; j >= 0; j -= flag) {
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

    /**
     * 位移法
     */
    public static void shellSort2(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 从第gap个元素开始，逐个对其所在的组进行插入排序
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        // 移动
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    // 退出while循环后，就给temp找到插入位置
                    arr[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
