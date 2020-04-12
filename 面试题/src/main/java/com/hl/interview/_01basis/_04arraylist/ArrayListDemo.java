package com.hl.interview._01basis._04arraylist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: Arrays工具类相关
 * @author: panhongtong
 * @create: 2020-04-12 22:06
 **/
public class ArrayListDemo {
    public static void main(String[] args) {
        //arraysTest();
        arraylist();
    }

    private static void arraylist() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        // 不要在for循环里对集合进行add、remove操作 ConcurrentModificationException
        //操作到最后一个元素的时候回出问题
//        for (String s : list) {
//            if ("3".equals(s)) {
//                list.remove(s);
//            }
//        }
        //推荐使用，底层通过Iterator实现
        list.removeIf("3"::equals);
        System.out.println(list);
    }

    /**
     * Arrays相关
     */
    private static void arraysTest() {
        // 正常使用
        List list = Arrays.asList(1, 2, 3, 4);
        System.out.println(list.size());
        System.out.println(list.get(0));
        System.out.println(list.get(1));

        System.out.println("-------------------------------");

        //错误使用1：不能传基本类型数组
//        int[] arr = {1, 2, 3};
//        List myList = Arrays.asList(arr);
//        System.out.println(myList.size());
//        System.out.println(myList.get(0));
//        System.out.println(myList.get(1));  //ArrayIndexOutOfBoundsException

        // 错误使用2：不能使用add、remove等方法 UnsupportedOperationException
//        list.add(8);
//        list.clear();
    }
}
