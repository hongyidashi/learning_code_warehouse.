package com.hl.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描述:
 *
 * 对于非负整数 X 而言，X 的数组形式是每位数字按从左到右的顺序形成的数组。例如，如果 X = 1231，那么其数组形式为 [1,2,3,1]。
 * 给定非负整数 X 的数组形式 A，返回整数 X+K 的数组形式。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-to-array-form-of-integer
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-22 10:06
 **/
public class Demo989 {

    public List<Integer> addToArrayForm(int[] A, int K) {
        List<Integer> res = new ArrayList<>();
        int cur = K;
        int i = A.length;

        while (--i >= 0 || cur > 0) {
            if (i >= 0) {
                cur += A[i];
            }
            res.add(cur % 10);

            cur = cur / 10;
        }

        Collections.reverse(res);

        return res;
    }

}
