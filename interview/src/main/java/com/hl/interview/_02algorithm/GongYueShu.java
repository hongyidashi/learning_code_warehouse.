package com.hl.interview._02algorithm;

import java.util.Scanner;

/**
 * 描述：公约数公倍数
 * 作者: panhongtong
 * 创建时间: 2020-05-15 12:07
 **/
public class GongYueShu {
    public static void main(String[] args) {
        int a, b, num1, num2, temp;
        System.out.println("please input two numbers:\n");
        Scanner sc = new Scanner(System.in);
        num1 = sc.nextInt();
        num2 = sc.nextInt();
        if (num1 < num2)/* 交换两个数，使大数放在num1上 */
        {
            temp = num1;
            num1 = num2;
            num2 = temp;
        }
        a = num1;
        b = num2;
        while (b != 0)/* 利用辗除法，直到b为0为止 */
        {
            temp = a % b;
            a = b;
            b = temp;
        }
        System.out.println("公约数" + a);
        System.out.println("公倍数" + num1 * num2 / a);
    }
}
