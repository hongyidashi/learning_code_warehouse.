package com.hl.algorithm;

/**
 * 描述:
 *
 * 给你一个数组 arr ，请你将每个元素用它右边最大的元素替换，如果是最后一个元素，用 -1 替换。
 * 完成所有替换操作后，请你返回这个数组。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-28 11:53
 **/
public class Demo1299 {
    public int[] replaceElements(int[] arr) {
        int maxV = arr[arr.length - 1];
        arr[arr.length - 1] = -1;

        for (int i = arr.length - 2; i >= 0; i--) {
            int temp = arr[i];
            arr[i] = maxV;
            if (temp > maxV) {
                maxV = temp;
            }
        }

        return arr;
    }
}
