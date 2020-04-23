package com.hl.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/23 09:11
 * @Description: 二分法查找
 */
public class BinarySearch01 {
    public static void main(String[] args) {
        int[] arr = {-1, 0, 6, 8, 11, 66, 55, 55, 55, 98};
        //System.out.println(binarySearch(arr, 0, arr.length -1, 11));
        System.out.println(binarySearch2(arr, 0, arr.length - 1, 55));
    }

    /**
     * 基础版，只能查找到一个
     */
    public static int binarySearch(int[] arr, int left, int right, int findVal) {
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if (findVal < midVal) {
            // 向左递归
            return binarySearch(arr, left, mid - 1, findVal);
        } else if (findVal > midVal) {
            return binarySearch(arr, mid + 1, right, findVal);
        } else {
            return mid;
        }
    }

    /**
     * 改进版，能查找出所有的
     */
    public static List binarySearch2(int[] arr, int left, int right, int findVal) {
        if (left > right) {
            return new ArrayList();
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if (findVal < midVal) {
            // 向左递归
            return binarySearch2(arr, left, mid - 1, findVal);
        } else if (findVal > midVal) {
            return binarySearch2(arr, mid + 1, right, findVal);
        } else {
            // 如果到了这里说明arr[mid]就是我们要找的值，要再向左或右看是否还有相同的值
            List<Integer> resList = new ArrayList<>();
            int temp = mid - 1;
            while (temp >= 0 && arr[temp] == findVal) {
                resList.add(temp);
                temp--;
            }
            resList.add(mid);
            temp = mid + 1;
            while (temp <= arr.length - 1 && arr[temp] == findVal) {
                resList.add(temp);
                temp++;
            }
            return resList;
        }
    }
}
