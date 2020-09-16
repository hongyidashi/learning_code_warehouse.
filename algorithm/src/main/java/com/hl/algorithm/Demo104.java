package com.hl.algorithm;

/**
 * 描述:
 *
 * 给定一个二叉树，找出其最大深度。二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-16 10:47
 **/
public class Demo104 {

    public static void main(String[] args) {
        // [3,9,20,null,null,15,7]
        TreeNode root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println(maxDepth(root));
    }

    /**
     * 递归
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHight = maxDepth(root.left);
        int rightHight = maxDepth(root.right);
        return Math.max(leftHight, rightHight) + 1;
    }
}
