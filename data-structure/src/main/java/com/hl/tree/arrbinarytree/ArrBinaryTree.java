package com.hl.tree.arrbinarytree;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/26 16:57
 * @Description:
 */
public class ArrBinaryTree {
    private int[] arr;

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrder() {
        preOrder(0);
    }

    /**
     * 前序遍历
     */
    public void preOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("该数组为空");
            return;
        }

        System.out.println(arr[index]);
        // 向左递归遍历
        if (index * 2 + 1 < arr.length) {
            preOrder(index * 2 + 1);
        }
        // 向右递归遍历
        if (index * 2 + 2 < arr.length) {
            preOrder(index * 2 + 2);
        }
    }
}
