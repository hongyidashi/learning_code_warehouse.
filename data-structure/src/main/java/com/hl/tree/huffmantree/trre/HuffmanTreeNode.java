package com.hl.tree.huffmantree.trre;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/28 09:53
 * @Description: 赫夫曼树节点
 */
@Setter
@Getter
public class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
    /**
     * 权值
     */
    private int value;

    /**
     * 指向左子节点
     */
    private HuffmanTreeNode left;

    /**
     * 指向右子节点
     */
    private HuffmanTreeNode right;

    /**
     * 前序遍历
     */
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    public HuffmanTreeNode(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HuffmanTreeNode{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(HuffmanTreeNode o) {
        return this.value - o.value;
    }
}
