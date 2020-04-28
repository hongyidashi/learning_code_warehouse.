package com.hl.tree.threadedbinarytree;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/26 16:58
 * @Description: 线索二叉树
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "大福");
        HeroNode node2 = new HeroNode(3, "断腿少女");
        HeroNode node3 = new HeroNode(6, "弟弟");
        HeroNode node4 = new HeroNode(8, "GDX");
        HeroNode node5 = new HeroNode(10, "大傻子");
        HeroNode node6 = new HeroNode(14, "智障");

        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
        threadedBinaryTree.setRootNode(root);

        //threadedBinaryTree.threadedNodes();
        //System.out.println(root.getLeft());
        //threadedBinaryTree.threadedBinaryTreeList();

        threadedBinaryTree.postThreadedNodes();
        System.out.println(node3.getRight());
    }
}
