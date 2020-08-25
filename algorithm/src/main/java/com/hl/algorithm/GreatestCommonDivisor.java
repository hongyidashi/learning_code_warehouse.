package com.hl.algorithm;

/**
 * 描述: 最大公约数
 * 作者: panhongtong
 * 创建时间: 2020-08-25 08:33
 **/
public class GreatestCommonDivisor {
    public static void main(String[] args) {
        System.out.println(getGreatestCommonDivisorV1(25, 5));
        System.out.println(getGreatestCommonDivisorV1(100, 80));
        System.out.println(getGreatestCommonDivisorV1(27,14));

        System.out.println("----------------------");

        System.out.println(getGreatestCommonDivisorV2(25,5));
        System.out.println(getGreatestCommonDivisorV2(100,80));
        System.out.println(getGreatestCommonDivisorV2(27,14));

        System.out.println("----------------------");

        System.out.println(getGreatestCommonDivisorV3(25,5));
        System.out.println(getGreatestCommonDivisorV3(100,80));
        System.out.println(getGreatestCommonDivisorV3(27,14));

    }

    /**
     * 更相减损术与移位相结合<br>
     *     目前最优解
     */
    public static int getGreatestCommonDivisorV3(int a, int b) {
        if (a == b) {
            return a;
        }
        if ((a & 1) == 0 && (b & 1) == 0) {
            // 均为偶数
            return getGreatestCommonDivisorV3(a >> 1, b >> 1) << 1;
        } else if ((a & 1) == 0 && (b & 1) != 0) {
            return getGreatestCommonDivisorV3(a >> 1, b);
        } else if ((a & 1) != 0 && (b & 1) == 0) {
            return getGreatestCommonDivisorV3(a, b >> 1);
        } else {
            int big = a > b ? a : b;
            int small = a < b ? a : b;

            return getGreatestCommonDivisorV3(big - small, small);
        }
    }

    /**
     * 更相减损术
     * 求最大公约数<br>
     *     避免了大整数取模可能出现的性能问题，但这不是一种稳定算法，如果两数相差悬殊，
     *     会大大增加运行次数
     */
    public static int getGreatestCommonDivisorV2(int a, int b) {
        if (a == b) {
            return a;
        }

        int big = a > b ? a : b;
        int small = a < b ? a : b;

        return getGreatestCommonDivisorV2(big - small, small);
    }

    /**
     * 辗转相除法
     * 求最大公约数<br>
     *     当两个数较大时 % 运算性能会较差
     */
    public static int getGreatestCommonDivisorV1(int a, int b) {
        int big = a > b ? a : b;
        int small = a < b ? a : b;

        if (big % small == 0) {
            return small;
        }
        return getGreatestCommonDivisorV1(big % small, small);
    }
}
