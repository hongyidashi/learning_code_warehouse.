package com.hl.tree.huffmantree.code;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/28 09:53
 * @Description: 赫夫曼编码节点
 */
@Setter
@Getter
public class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
    /**
     * 数据
     */
    private Byte data;

    /**
     * 权值
     */
    private int weight;

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

    public HuffmanTreeNode(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(HuffmanTreeNode o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "HuffmanTreeNode{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }
}
