package com.hl.algorithm;

/**
 * 描述:
 *
 * 给你一个由 不同 整数组成的整数数组 arr 和一个整数 k 。
 * 每回合游戏都在数组的前两个元素（即 arr[0] 和 arr[1] ）之间进行。比较 arr[0] 与 arr[1] 的大小，
 * 较大的整数将会取得这一回合的胜利并保留在位置 0 ，较小的整数移至数组的末尾。当一个整数赢得 k 个连续回合时，游戏结束，
 * 该整数就是比赛的 赢家 。
 * 返回赢得比赛的整数。
 * 题目数据 保证 游戏存在赢家。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-the-winner-of-an-array-game
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-20 09:26
 **/
public class Demo1535 {

    public int getWinner(int[] arr, int k) {
        int count = 0;

        for (int i = 1; i < arr.length; i++) {
            if (arr[0] > arr[i]) {
                count++;
            } else {
                arr[0] = arr[i];
                count = 1;
            }
            if (count == k) {
                return arr[0];
            }
        }

        return arr[0];
    }
}
