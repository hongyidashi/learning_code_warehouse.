package com.hl.tree.arrbinarytree;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/26 16:08
 * @Description: 顺序存储二叉树
 */
public class ArrBinaryTreeDemo02 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        arrBinaryTree.preOrder();
    }
}