package com.hl.tree.binarytree;

import lombok.Setter;

import java.util.Stack;

@Setter
public class BinaryTree {
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
     * 利用栈进行前序遍历
     */
    public void preOrderByStack() {
        Stack<HeroNode> stack = new Stack<>();
        HeroNode heroNode = rootNode;

        while (heroNode != null || !stack.isEmpty()) {
            // 迭代访问节点的左孩子，并入栈
            while (heroNode != null) {
                System.out.println(heroNode);
                stack.push(heroNode);
                heroNode = heroNode.getLeft();
            }

            // 如果节点没有左孩子，则弹出栈顶节点，访问节点又孩子
            if (!stack.isEmpty()) {
                heroNode = stack.pop();
                heroNode = heroNode.getRight();
            }
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