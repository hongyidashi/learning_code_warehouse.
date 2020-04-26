package com.hl.tree.binarytree;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/26 11:55
 * @Description: 二叉树遍历
 */
public class BinaryTreeDemo01 {
    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();

        HeroNode heroNode1 = new HeroNode(1, "大福");
        HeroNode heroNode2 = new HeroNode(2, "断腿少女");
        HeroNode heroNode3 = new HeroNode(3, "弟弟");
        HeroNode heroNode4 = new HeroNode(4, "GDX");
        HeroNode heroNode5 = new HeroNode(5, "大宝贝");

        binaryTree.setRootNode(heroNode1);
        heroNode1.setLeft(heroNode2);
        heroNode1.setRight(heroNode3);
        heroNode3.setRight(heroNode4);
        heroNode3.setLeft(heroNode5);

        binaryTree.delNode(3);
//        System.out.println("前序查找：" + binaryTree.preOrderSearch(5));

        System.out.println("前序遍历");
        binaryTree.preOrder();
        System.out.println();
//        System.out.println("中序遍历");
//        binaryTree.infixOrder();
//        System.out.println();
//        System.out.println("后序遍历");
//        binaryTree.postOrder();
    }
}


