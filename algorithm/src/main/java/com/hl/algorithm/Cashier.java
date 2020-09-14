package com.hl.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: panhongtong
 * 创建时间: 2020-09-14 20:57
 * <p>
 * 超市里正在举行打折活动，每隔 n 个顾客会得到 discount 的折扣。
 * 超市里有一些商品，第 i 种商品为 products[i] 且每件单品的价格为 prices[i] 。
 * 结账系统会统计顾客的数目，每隔 n 个顾客结账时，该顾客的账单都会打折，折扣为 discount （也就是如果原本账单为 x ，
 * 那么实际金额会变成 x - (discount * x) / 100 ），然后系统会重新开始计数。
 * 顾客会购买一些商品， product[i] 是顾客购买的第 i 种商品， amount[i] 是对应的购买该种商品的数目。
 * 请你实现 Cashier 类：
 * Cashier(int n, int discount, int[] products, int[] prices) 初始化实例对象，参数分别为打折频率 n ，
 * 折扣大小 discount ，超市里的商品列表 products 和它们的价格 prices 。
 * double getBill(int[] product, int[] amount) 返回账单的实际金额（如果有打折，请返回打折后的结果）。
 * 返回结果与标准答案误差在 10^-5 以内都视为正确结果。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/apply-discount-every-n-orders
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 **/
public class Cashier {

    private int n, discount, count;
    private Map<Integer, Integer> priceMap;

    public Cashier(int n, int discount, int[] products, int[] prices) {
        this.n = n;
        this.discount = discount;
        this.priceMap = new HashMap<>();
        for (int i = 0; i < products.length; i++) {
            priceMap.put(products[i], prices[i]);
        }
    }

    public double getBill(int[] product, int[] amount) {
        double billAmount = 0;
        for (int i = 0; i < product.length; i++) {
            billAmount += priceMap.get(product[i]) * amount[i];
        }
        count++;

        return (count % n == 0) ? billAmount - (discount * billAmount) / 100.0 : billAmount;
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7};
        int[] arr2 = {100, 200, 300, 400, 300, 200, 100};
        Cashier cashier = new Cashier(3, 50, arr1, arr2);
        int[] arr3 = {1, 2};
        int[] arr4 = {1, 2};
        System.out.println(cashier.getBill(arr3, arr4));
        System.out.println(cashier.getBill(arr3, arr4));
        System.out.println(cashier.getBill(arr3, arr4));
        System.out.println(cashier.getBill(arr3, arr4));
    }
}
