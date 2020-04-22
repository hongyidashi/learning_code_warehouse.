package com.hl.sort;

import java.util.Arrays;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/22 16:34
 * @Description: 桶排序
 */
public class RadixSort07 {
    public static void main(String[] args) {
        int[] arr = {98, 10, 6, 585, 777};
        radixSort(arr);
    }

    public static void radixSort(int[] arr) {
        // 定义一个二维数组，表示10个桶
        int[][] bucket = new int[10][arr.length];

        // 一个一维数组来来记录桶的情况
        int[] bucketElementCount = new int[10];

        // 找到最大值并获得最大长度
        int max = arr[0];
        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }
        int maxLength = (max+"").length();

        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            // 进行倒序处理
            for (int j = 0; j < arr.length; j++) {
                // 获得各位数
                int digitOfElement = arr[j] / n % 10;
                // 放入对应的桶中并记录  第一次进来，如果数为10，则表示 在0号桶的第0个位置
                bucket[digitOfElement][bucketElementCount[digitOfElement]] = arr[j];
                // 桶内指针移动一位
                bucketElementCount[digitOfElement]++;
            }

            // 将桶中数据取出放入原数组
            int index = 0;
            for (int k = 0; k < bucketElementCount.length; k++) {
                if (bucketElementCount[k] != 0) {
                    for (int l = 0; l < bucketElementCount[k]; l++) {
                        arr[index++] = bucket[k][l];
                    }
                }
                bucketElementCount[k] = 0;
            }
        }

        System.out.println(Arrays.toString(arr));
    }
}
