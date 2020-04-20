package com.hl.sort;

import java.util.Arrays;

/**
 * @description: 选择排序
 * @author: panhongtong
 * @create: 2020-04-19 15:49
 **/
public class SelectSort02 {
    public static void main(String[] args) {
        int[] arr = {8, -5, 3, 20, -6, 2};
        //int[] arr = {2,-1,3,4,5,9};
        selectSort(arr);
    }

    public static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            //中间变量，记录下标
            int index = i;
            int min = arr[i];

            //比较
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                    index = j;
                }
            }

            //交换
            if (index != i) {
                arr[index] = arr[i];
                arr[i] = min;
            }

        }


        System.out.println(Arrays.toString(arr));

    }
}
