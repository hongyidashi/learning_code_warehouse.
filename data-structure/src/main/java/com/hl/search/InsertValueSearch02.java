package com.hl.search;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/23 16:02.
 * @Description: 插值查找
 */
public class InsertValueSearch02 {
    public static void main(String[] args) {
        int[] arr = new int[15];
        for (int i = 0; i < 10; i++) {
            arr[i] = i;
        }
        arr[10] = 100;
        arr[11] = 101;
        arr[12] = 102;
        arr[13] = 103;
        arr[14] = 104;
        System.out.println(insertValueSearch(arr, 0, arr.length - 1, 102));
    }

    public static int insertValueSearch(int[] arr, int left, int right, int findVal) {
        if (left > right || findVal < arr[0] || findVal > arr[arr.length - 1]) {
            return -1;
        }
        int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];
        if (findVal < midVal) {
            // 向左递归
            return insertValueSearch(arr, left, mid -1, findVal);
        } else if (findVal > midVal) {
            return insertValueSearch(arr, mid + 1, right, findVal);
        } else {
            return mid;
        }
    }
}
