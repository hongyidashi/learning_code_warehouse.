package com.hl.sort;

import java.util.Arrays;

/**
 * @description: 冒泡排序
 * @author: panhongtong
 * @create: 2020-04-19 15:16
 **/
public class BubbleSort01 {
    public static void main(String[] args) {
        //int[] arr = {8, -5, 3, 20, -6, 2};
        int[] arr = {2,-1,3,4,5,9};
        bubbleSort(arr);
    }

    public static void bubbleSort(int[] arr) {
        //设置一个临时变量
        int temp;

        //设置标识，是否发生交换
        boolean flag = false;

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            if (!flag) {
                break;
            } else {
                flag = false;
            }
            System.out.println(Arrays.toString(arr));
        }

    }
}
