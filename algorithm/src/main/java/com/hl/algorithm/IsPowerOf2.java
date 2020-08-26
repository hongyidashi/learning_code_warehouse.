package com.hl.algorithm;

/**
 * 描述: 判断一个数是否为2的整数次幂
 * 作者: panhongtong
 * 创建时间: 2020-08-26 08:32
 **/
public class IsPowerOf2 {
    public static void main(String[] args) {
        System.out.println(isPowerOf2(4));
        System.out.println(isPowerOf2(32));
        System.out.println(isPowerOf2(64));
        System.out.println(isPowerOf2(41));
    }

    /**
     * 二进制下，二的整数次幂 最高位是 1，其余位是 0；
     * -1 后 二进制数字全为 1；
     * 两数进行 & 运算则为 0
     */
    public static boolean isPowerOf2(int num) {
        return (num & num - 1) == 0;
    }
}
