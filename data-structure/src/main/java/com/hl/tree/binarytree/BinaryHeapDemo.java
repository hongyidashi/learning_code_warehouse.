package com.hl.tree.binarytree;

/**
 * 描述: 二叉堆
 * 作者: panhongtong
 * 创建时间: 2020-08-19 08:56
 **/
public class BinaryHeapDemo {
    public static void main(String[] args) {
        int[] arr = {1,3,2,6,5,7,8,9,10,0};
        upAdjust(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    /**
     * "上浮"调整
     *
     * @param arr 待"上浮"的堆
     */
    public static void upAdjust(int[] arr) {
        // 子节点索引
        int childIndex = arr.length - 1;
        // 父节点索引
        int parentIndex = (childIndex - 1) / 2;
        // 保存子节点的值，用于赋值
        int temp = arr[childIndex];

        // 判断是否满足上浮条件
        while (childIndex > 0 && temp < arr[parentIndex]) {
            // 父子节点值交换
            arr[childIndex] = arr[parentIndex];
            childIndex = parentIndex;
            parentIndex = (parentIndex - 1) / 2;
        }
        // 赋值
        arr[childIndex] = temp;
    }
}