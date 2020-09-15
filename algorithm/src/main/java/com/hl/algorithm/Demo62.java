package com.hl.algorithm;

import java.util.Arrays;

/**
 * 描述:
 *
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 * 问总共有多少条不同的路径？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-paths
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-15 21:56
 **/
public class Demo62 {

    public static void main(String[] args) {
        System.out.println(uniquePaths(7, 3));
        System.out.println(uniquePaths2(7, 3));
        show(new int[7][3]);
    }

    public static int uniquePaths(int m, int n) {
        int[][] arr = new int[m][n];

        Arrays.fill(arr[0], 1);

        for (int i = 1; i < m; i++) {
            arr[i][0] = 1;

            for (int j = 1; j < n; j++) {
                arr[i][j] = arr[i - 1][j] + arr[i][j - 1];
            }

        }

        return arr[m - 1][n - 1];
    }

    public static int uniquePaths2(int m, int n) {
        int[] arr = new int[m];
        Arrays.fill(arr, 1);

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                arr[j] = arr[j - 1] + arr[j];
            }
        }

        return arr[n - 1];
    }

    public static void show(int[][] number){
        for(int i = 0; i < number.length; i++){ 
            for(int j = 0; j < number[i].length; j++){ 
                System.out.print(number[i][j]);
            } 
            System.out.println();
        } 
    }
}
