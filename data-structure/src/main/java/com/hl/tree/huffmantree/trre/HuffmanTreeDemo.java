package com.hl.tree.huffmantree.trre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/28 09:52
 * @Description: 赫夫曼树
 */
public class HuffmanTreeDemo {
    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        HuffmanTreeNode rootNode = createHuffmanTree(arr);
        preOrder(rootNode);
    }

    /**
     * 创建一棵赫夫曼数并返回头节点
     */
    public static HuffmanTreeNode createHuffmanTree(int[] arr) {
        List<HuffmanTreeNode> nodes = new ArrayList<>();
        for (int value : arr) {
            nodes.add(new HuffmanTreeNode(value));
        }
        Collections.sort(nodes);

        while (nodes.size() > 1) {
            // 取出最小的两棵二叉树建立新的二叉树
            HuffmanTreeNode leftNode = nodes.get(0);
            HuffmanTreeNode rightNode = nodes.get(1);
            HuffmanTreeNode parentNode = new HuffmanTreeNode(leftNode.getValue() + rightNode.getValue());
            parentNode.setLeft(leftNode);
            parentNode.setRight(rightNode);

            // 在集合中移除最小的两棵树，并将新树加入集合中
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parentNode);

            // 重新排序
            Collections.sort(nodes);
        }

        return nodes.get(0);
    }

    /**
     * 前序遍历
     * @param rootNode 树的根节点
     */
    public static void preOrder(HuffmanTreeNode rootNode) {
        if (rootNode == null) {
            System.out.println("该树为空！");
            return;
        }
        rootNode.preOrder();
    }
}