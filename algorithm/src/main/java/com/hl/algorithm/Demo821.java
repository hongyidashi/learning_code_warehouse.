package com.hl.algorithm;

/**
 * 描述:
 *
 * 给定一个字符串 S 和一个字符 C。返回一个代表字符串 S 中每个字符到字符串 S 中的字符 C 的最短距离的数组。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-23 10:30
 **/
public class Demo821 {

    public int[] shortestToChar(String S, char C) {
        int len = S.length();
        int[] ans = new int[len];
        int prev = Integer.MIN_VALUE / 2;

        char[] chars = S.toCharArray();

        for (int i = 0; i < len; i++) {
            if (chars[i] == C) {
                prev = i;
            }
            ans[i] = i - prev;
        }

        prev = Integer.MAX_VALUE / 2;
        for (int i = len - 1; i >= 0; i--) {
            if (chars[i] == C) {
                prev = i;
            }
            ans[i] = Math.min(ans[i], prev - i);
        }

        return ans;
    }
}
