package com.hl.algorithm;

/**
 * 描述: 二叉树
 * 作者: panhongtong
 * 创建时间: 2020-09-16 10:49
 **/
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
