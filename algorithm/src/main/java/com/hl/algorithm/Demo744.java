package com.hl.algorithm;

/**
 * 描述:
 *
 * 给你一个排序后的字符列表 letters ，列表中只包含小写英文字母。另给出一个目标字母 target，
 * 请你寻找在这一有序列表里比目标字母大的最小字母。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-17 10:26
 **/
public class Demo744 {

    public static void main(String[] args) {
        //char[] letters = new char[]{'c','f','j'};
        char[] letters = new char[]{'e','e','e','e','e','e','n','n','n','n'};
        System.out.println(nextGreatestLetter(letters, 'e'));
    }

    public static char nextGreatestLetter(char[] letters, char target) {
        for (char c: letters) {
            if (c > target) {
                return c;
            }
        }
        return letters[0];
    }
}
