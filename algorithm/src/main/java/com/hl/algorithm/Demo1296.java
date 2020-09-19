package com.hl.algorithm;

import java.util.PriorityQueue;

/**
 * 描述:
 *
 * 给你一个整数数组 nums 和一个正整数 k，请你判断是否可以把这个数组划分成一些由 k 个连续数字组成的集合。
 * 如果可以，请返回 True；否则，返回 False。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/divide-array-in-sets-of-k-consecutive-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-19 14:23
 **/
public class Demo1296 {

    public boolean isPossibleDivide(int[] nums, int k) {
        int len = nums.length;

        // 如果不是K的倍数，一定不能划分为k个
        if (len % k != 0) {
            return false;
        }

        // 优先队列是小顶堆，小的元素会被放在前面
        PriorityQueue<Integer> queue = new PriorityQueue<>(len);
        for (int num : nums) {
            queue.offer(num);
        }

        while (!queue.isEmpty()) {
            Integer top = queue.poll();
            for (int i = 1; i < k; i++) {
                if (!queue.remove(top + i)) {
                    return false;
                }
            }
        }

        return true;
    }

}
