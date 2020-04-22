package com.hl.sort;

import java.util.Arrays;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/22 10:51
 * @Description: 快速排序
 */
public class QuickSort05 {
    public static void main(String[] args) {
        //int[] arr = {8, 11, 0, 9, -65, 1000, -6, 7, 66, 0};
        int[] arr = {0,0,0,8,10,0};//0 0 0 0 8 10
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int left, int right) {
        // 左、右下标，中值
        int l = left;
        int r = right;
        int pivot = arr[(l + r) / 2];
        int temp;

        //如果左边已经没有比pivot大的了，会用pivot来交换
        while (l < r) {
            //如果取到了pivot也会退出
            while (arr[l] < pivot) {
                l += 1;
            }
            while (arr[r] > pivot) {
                r -= 1;
            }
            //如果l >= r，说明已经达到本次排序要求，比pivot小的在左，大的在右
            if (l >= r) {
                break;
            }
            //交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            //交换完后发现arr[l] == pivot，则r--，防止出现死循环
            if (arr[l] == pivot) {
                r -= 1;
            }
            if (arr[r] == pivot) {
                l += 1;
            }
        }
        //结束了一轮最外层的while之后，必定r == l，会出现死循环
        if (l == r) {
            l += 1;
            r -= 1;
        }
        if (left < r) {
            quickSort(arr, left, r);
        }
        if (right > l) {
            quickSort(arr, l, right);
        }
    }
}
