package com.hl.algorithm;

import java.util.Arrays;

/**
 * 描述:
 * <p>
 * 给你一个正整数的数组 A（其中的元素不一定完全不同），
 * 请你返回可在 一次交换（交换两数字 A[i] 和 A[j] 的位置）后得到的、按字典序排列小于 A 的最大可能排列。
 * 如果无法这么操作，就请返回原数组。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/previous-permutation-with-one-swap
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * 作者: panhongtong
 * 创建时间: 2020-09-16 21:06
 **/
public class Demo1053 {

    public static void main(String[] args) {
        // [1,9,4,6,7]
        int[] arr = new int[]{1, 9, 4, 6, 7};
        System.out.println(Arrays.toString(prevPermOpt1(arr)));
    }

    public static int[] prevPermOpt1(int[] arr) {
        int len = arr.length;

        for (int i = len - 2; i >= 0; i--) {
            int max = i + 1;
            for (int j = i + 1; j < len; j++) {
                if (arr[j] > arr[max] && arr[j] < arr[i]) {
                    max = j;
                }
            }
            if (arr[i] > arr[max]) {
                int temp = arr[i];
                arr[i] = arr[max];
                arr[max] = temp;
                return arr;
            }

        }

        return arr;
    }
}
