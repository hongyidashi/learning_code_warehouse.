package com.hl.algorithm;

import java.util.Stack;

/**
 * 描述:
 * 作者: panhongtong
 * 创建时间: 2020-09-18 21:43
 **/
public class Demo678 {

    public static void main(String[] args) {
        System.out.println(checkValidString("(())((())()()(*)(*()(())())())()()((()())((()))(*"));
    }

    public static boolean checkValidString1(String s) {
        Stack<Integer> left = new Stack<>();
        Stack<Integer> star = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            String temp = s.substring(i, i + 1);
            if (temp.equals("(")) {
                left.push(i);
                continue;
            }
            if (temp.equals("*")) {
                star.push(i);
                continue;
            }
            if (")".equals(temp)) {
                if (!left.empty()) {
                    left.pop();
                    continue;
                }
                if (!star.empty()) {
                    star.pop();
                    continue;
                }
                return false;
            }
        }

        while (!left.empty()) {
            if (star.empty()) {
                return false;
            }
            if (left.peek() < star.peek()) {
                left.pop();
                star.pop();
            } else {
                return false;
            }
        }
        return true;
    }

}
