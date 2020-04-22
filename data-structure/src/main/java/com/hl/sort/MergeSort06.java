package com.hl.sort;

import java.util.Arrays;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/22 14:21
 * @Description: 归并排序
 */
public class MergeSort06 {
    public static void main(String[] args) {
        int[] arr = {8, -5, 3, 20, -6, 2};
        mergeSort(arr, 0, arr.length - 1, new int[arr.length]);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 合并 + 分解
     */
    public static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            // 左递归分解
            mergeSort(arr, left, mid, temp);
            // 右递归分解
            mergeSort(arr, mid + 1, right, temp);
            // 合并
            merge(arr, left, mid, right, temp);
        }
    }

    /**
     * 合并
     *
     * @param arr   目标数组
     * @param left  左边有序序列的开始索引
     * @param mid   中间索引
     * @param right 右边有序序列的开始索引
     * @param temp  做中转的数组
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        // 左、右索引
        int i = left;
        int j = mid + 1;
        // temp数组索引
        int t = 0;

        // 将元素有序的放入temp数组
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[t] = arr[i];
                t++;
                i++;
            } else {
                temp[t] = arr[j];
                t++;
                j++;
            }
        }
        // 将剩余的全部放入temp数组
        while (i <= mid) {
            temp[t] = arr[i];
            t++;
            i++;
        }
        while (j <= right) {
            temp[t] = arr[j];
            t++;
            j++;
        }
        // 复制到原数组
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            t++;
            tempLeft++;
        }
    }
}
