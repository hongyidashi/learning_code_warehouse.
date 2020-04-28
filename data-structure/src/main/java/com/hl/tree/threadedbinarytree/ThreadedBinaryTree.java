package com.hl.tree.threadedbinarytree;

import lombok.Setter;

@Setter
public class ThreadedBinaryTree {
    private HeroNode rootNode;

    /**
     * 前驱节点指针
     */
    private HeroNode preNode;

    /**
     * 父节点指针
     */
    private HeroNode parentNode;

    /**
     * 中序遍历线索化二叉树
     */
    public void threadedBinaryTreeList() {
        HeroNode curNode = rootNode;
        while (curNode != null) {
            // 找到第一个LeftType == 1的节点
            while (curNode.getLeftType() == 0) {
                curNode = curNode.getLeft();
            }

            // 打印这个节点
            System.out.println(curNode);

            // 如果右指针是指向后继节点，就一直打印
            while (curNode.getRightType() == 1) {
                curNode = curNode.getRight();
                System.out.println(curNode);
            }

            // 替换这个遍历的节点
            curNode = curNode.getRight();
        }
    }

    /**
     * 后序线索化节点
     */
    public void postThreadedNodes() {
        postThreadedNodes(rootNode);
    }
    private void postThreadedNodes(HeroNode node) {
        if (node == null) {
            return;
        }

        // 线索化左子树
        threadedNodes(node.getLeft());
        // 线索化右子树
        threadedNodes(node.getRight());

        // 线索化当前节点
        // 处理前驱节点
        if (node.getLeft() == null) {
            node.setLeft(preNode);
            node.setLeftType(1);
        }

        // 处理后继节点
        if (preNode != null && preNode.getRight() == null) {
            preNode.setRight(node);
            preNode.setRightType(1);
        }
        preNode = node;
    }

    /**
     * 中序线索化二叉树
     */
    public void threadedNodes() {
        threadedNodes(rootNode);
    }
    private void threadedNodes(HeroNode node) {
        if (node == null) {
            return;
        }

        // 线索化左子树
        threadedNodes(node.getLeft());

        // 线索化当前节点
        // 处理前驱节点
        if (node.getLeft() == null) {
            node.setLeft(preNode);
            node.setLeftType(1);
        }

        // 处理后继节点
        if (preNode != null && preNode.getRight() == null) {
            preNode.setRight(node);
            preNode.setRightType(1);
        }
        preNode = node;

        // 线索化右子树
        threadedNodes(node.getRight());
    }
}