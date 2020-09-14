package com.hl.algorithm;

/**
 * 描述:
 * 给你一棵根为 root 的二叉树，请你返回二叉树中好节点的数目。
 * 「好节点」X 定义为：从根到该节点 X 所经过的节点中，没有任何节点的值大于 X 的值。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-14 21:33
 **/
public class GoodNodes {

    int count = 0;

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3,new TreeNode(1,new TreeNode(3),null),new TreeNode(4,new TreeNode(1),new TreeNode(5)));

        GoodNodes goodNodes = new GoodNodes();
        System.out.println(goodNodes.goodNodes(root));
    }

    public int goodNodes(TreeNode root) {
        recur(root, root.val);
        return count;
    }

    public void recur(TreeNode node,int max) {
        if (node == null) {
            return;
        }

        if (node.val >= max) {
            count++;
            max = node.val;
        }

        recur(node.left, max);
        recur(node.right, max);

    }
}

class TreeNode {
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