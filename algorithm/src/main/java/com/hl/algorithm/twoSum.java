package com.hl.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 两数之和
 * 作者: panhongtong
 * 创建时间: 2020-06-09 22:34
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 **/
public class twoSum {
    public static void main(String[] args) throws IllegalAccessException {
        int[] nums = new int[]{2, 9, 11, 15, 7};
        int target = 9;
        //int[] ints = twoSum1(nums, target);
        //int[] ints = twoSum2(nums, target);
        int[] ints = twoSum3(nums, target);
        System.out.println(Arrays.toString(ints));
    }

    /**
     * 暴力解法
     * 遍历每一个元素，判断是否存在target - i = j
     */
    public static int[] twoSum1(int[] nums, int target) throws IllegalAccessException {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalAccessException("未找到符合要求的值");
    }

    /**
     * 两遍哈希表
     * 将数组的 值，下标 放入hashmap，遍历数组，看是否存在这样符合条件的key，如果有就返回
     */
    public static int[] twoSum2(int[] nums, int target) throws IllegalAccessException {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int condition = target - nums[i];
            if (map.containsKey(condition) && map.get(condition) != i) {
                return new int[]{i, map.get(condition)};
            }
        }

        throw new IllegalAccessException("未找到符合要求的值");
    }

    /**
     * 一遍哈希表
     * 将数组的 值，下标 放入hashmap，遍历数组，看是否存在这样符合条件的key，如果有就返回
     */
    public static int[] twoSum3(int[] nums, int target) throws IllegalAccessException {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
            int condition = target - nums[i];
            if (map.containsKey(condition) && map.get(condition) != i) {
                return new int[]{i, map.get(condition)};
            }
        }

        throw new IllegalAccessException("未找到符合要求的值");
    }
}
