package com.hl.algorithm;

/**
 * 描述:
 *
 * 给定一个含有 M x N 个元素的矩阵（M 行，N 列），请以对角线遍历的顺序返回这个矩阵中的所有元素
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-18 11:21
 **/
public class Demo498 {

    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix.length == 0) {
            return new int[0];
        }

        int rowLength = matrix.length;
        int columnLength = matrix[0].length;

        // 返回用的数组
        int[] result = new int[rowLength * columnLength];

        // 循环次数
        int count = rowLength + columnLength - 1;

        int index = 0;
        int m = 0;
        int n = 0;

        for (int i = 0; i < count; i++) {
            // 往右上方向遍历
            if (i % 2 == 0) {
                while (m >= 0 && n < columnLength) {
                    result[index++] = matrix[m--][n++];
                }
                // 边界情况
                if (n < columnLength) {
                    m++;
                } else {
                    m += 2;
                    n--;
                }
            } else {
                while (n >= 0 && m < rowLength) {
                    result[index++] = matrix[m++][n--];
                }
                // 边界情况
                if (m < rowLength) {
                    n++;
                } else {
                    n += 2;
                    m--;
                }
            }
        }

        return result;
    }
}
