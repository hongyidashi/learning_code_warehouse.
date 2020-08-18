package com.hl.basis.for_;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 作者: panhongtong
 * 创建时间: 2020-08-13 11:02
 **/
public class ForDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        forMethod(list);
    }

    public static void forMethod(List<Integer> list) {
        for (Integer i : list) {
            System.out.println(i);
            if (i == 3){
                return;
            }
        }
        System.out.println("不可能到这");
    }
}
