package com.hl.sort;

import java.util.Arrays;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/21 11:47
 * @Description: 插入排序
 */
public class InsertSort03 {
    public static void main(String[] args) {
        int[] arr = {8, -5, 3, 20, -6, 2};
        insertSort(arr);
    }

    public static void insertSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            //定义待插入的数
            int insertVal = arr[i + 1];
            int insertIndex = i;

            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }
            arr[insertIndex + 1] = insertVal;
        }
        System.out.println(Arrays.toString(arr));
    }
}
