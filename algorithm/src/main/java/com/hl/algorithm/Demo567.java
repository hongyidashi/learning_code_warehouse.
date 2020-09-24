package com.hl.algorithm;

import java.util.Arrays;

/**
 * 描述:
 *
 * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
 * 换句话说，第一个字符串的排列之一是第二个字符串的子串。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-24 10:13
 **/
public class Demo567 {

    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 > len2) {
            return false;
        }
        int[] arr = new int[26];

        for (int i = 0; i < len1; i++) {
            ++arr[s1.charAt(i) - 'a'];
            --arr[s2.charAt(i) - 'a'];
        }

        for (int i = len1; i < len2; i++) {
            if (isEqueal(arr)) {
                return true;
            }
            // 滑动
            ++arr[s2.charAt(i - len1) - 'a'];
            --arr[s2.charAt(i) - 'a'];
        }

        return isEqueal(arr);
    }

    public static boolean isEqueal(int[] arr) {
        return Arrays.stream(arr).allMatch(count -> count == 0);
    }

}
