package com.hl.tree.binarytree;

import java.util.Arrays;

/**
 * 描述: 二叉堆-小顶堆Demo
 * 作者: panhongtong
 * 创建时间: 2020-08-19 08:56
 **/
public class BinaryHeapDemo {
    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 6, 5, 7, 8, 9, 10, 0};
        upAdjust(arr);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{7, 1, 3, 10, 5, 2, 8, 9, 6};
        buildHeap(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void buildHeap(int[] arr) {
        // 从最后一个非叶子节点开始以此下沉
        for (int i = (arr.length - 2)/2; i >=0 ; i--) {
            downAdjust(arr, i,arr.length);
        }
    }

    /**
     * "下沉"调整
     * @param arr 待调整的数组
     * @param parentIndex 要"下沉"的节点
     * @param length 堆的有效大小
     */
    public static void downAdjust(int[] arr, int parentIndex, int length) {
        // temp保存要下沉的节点的值
        int temp = arr[parentIndex];
        int childIndex = 2 * parentIndex + 1;
        while (childIndex < length) {
            // 如果有右子节点，且又子节点的值小于左子节点的值，则定位到右子节点
            if (childIndex + 1 < length && arr[childIndex+1] < arr[childIndex]) {
                childIndex = childIndex + 1;
            }
            // 如果父节点小于子节点则退出
            if (temp <= arr[childIndex]) {
                break;
            }
            // 交换
            arr[parentIndex] = arr[childIndex];
            parentIndex = childIndex;
            childIndex = parentIndex * 2 + 1;
        }
        arr[parentIndex] = temp;
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