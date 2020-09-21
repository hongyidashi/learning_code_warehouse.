package com.hl.algorithm;

/**
 * 描述:
 *
 * 给你一个字符串 s 和一个整数数组 cost ，其中 cost[i] 是从 s 中删除字符 i 的代价。
 * 返回使字符串任意相邻两个字母不相同的最小删除成本。
 * 请注意，删除一个字符后，删除其他字符的成本不会改变。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-deletion-cost-to-avoid-repeating-letters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-21 14:34
 **/
public class Demo1578 {

    public int minCost(String s, int[] cost) {
        int res = 0;
        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            int max = cost[i];
            int sum = max;
            char c = chars[i];
            while (i + 1 < chars.length && c == chars[i + 1]) {
                sum += cost[++i];
                max = Math.max(cost[i], max);
            }
            if (max != sum) {
                res += sum - max;
            }
        }

        return res;
    }
}
