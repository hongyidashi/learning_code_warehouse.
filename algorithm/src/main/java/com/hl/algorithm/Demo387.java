package com.hl.algorithm;

import java.util.HashMap;

/**
 * 描述:
 *
 * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-15 21:27
 **/
public class Demo387 {

    public static void main(String[] args) {
        //String s = "leetcode";
        String s = "loveleetcode";
        System.out.println(firstUniqChar(s));
    }

    public static int firstUniqChar(String s) {
        char[] array = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>(array.length);

        for (char c : array) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < array.length; i++) {
            if (map.get(array[i]) == 1) {
                return i;
            }
        }

        return -1;
    }
}
