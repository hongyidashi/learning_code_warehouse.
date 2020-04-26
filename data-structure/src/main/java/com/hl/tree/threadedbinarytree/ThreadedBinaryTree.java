package com.hl.tree.threadedbinarytree;

import com.hl.tree.binarytree.HeroNode;
import lombok.Setter;

@Setter
public class ThreadedBinaryTree {
    private HeroNode rootNode;

    public void delNode(int no) {
        if (rootNode == null) {
            System.out.println("该二叉树为空");
            return;
        }
        if (rootNode.getNo() == no) {
            rootNode = null;
            return;
        }
        rootNode.delNode(no);
    }

    /**
     * 前序查找
     */
    public HeroNode preOrderSearch(int no) {
        if (rootNode != null) {
            return rootNode.preOrderSearch(no);
        } else {
            System.out.println("该二叉树为空");
            return null;
        }
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        if (rootNode != null) {
            rootNode.preOrder();
        } else {
            System.out.println("该二叉树为空，无法遍历");
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (rootNode != null) {
            rootNode.infixOrder();
        } else {
            System.out.println("该二叉树为空，无法遍历");
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        if (rootNode != null) {
            rootNode.postOrder();
        } else {
            System.out.println("该二叉树为空，无法遍历");
        }
    }
}