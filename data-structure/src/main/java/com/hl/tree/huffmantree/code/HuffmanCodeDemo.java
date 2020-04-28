package com.hl.tree.huffmantree.code;

import java.util.*;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/28 14:17
 * @Description: 赫夫曼编码
 */
public class HuffmanCodeDemo {
    public static void main(String[] args) {
        String content = "GDX,fuck you man fuck shit ok? fuck fuck fuck you";

        byte[] bytes = huffmanZip(content);
        decode(bytes);
    }

    /**
     * 赫夫曼编码
     */
    private static Map<Byte, String> HUFFMANCODES = new HashMap<>();

    //--------------------------------解码--------------------------------
    /**
     * 解码
     */
    public static byte[] decode(byte[] huffmanBytes) {
        // 得到huffmanByte对应的二进制字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            byte b = huffmanBytes[i];
            // 判断是不是最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(b,!flag));
        }



        return null;
    }

    /***
     * 将一个byte转换成一个二进制字符串
     * @param flag 是否需要补高位
     */
    private static String byteToBitString(byte b, boolean flag) {
        int temp = b;
        // 如果是正数存在补高位的情况
        if (flag) {
            temp |= 256;
        }
        // 返回的是temp对应的二进制的补码
        String str = Integer.toBinaryString(temp);
        if (flag) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }
    }
    //--------------------------------解码--------------------------------


    //--------------------------------编码--------------------------------
    /**
     * 获得用赫夫曼编码处理后的字节数组
     */
    public static byte[] huffmanZip(String content) {
        // 获得内容的字节数组
        byte[] bytes = content.getBytes();
        // 获得nodes
        List<HuffmanTreeNode> nodes = getNodes(bytes);
        // 根据nodes获得赫夫曼树
        HuffmanTreeNode rootNode = createHuffmanTree(nodes);
        // 生成赫夫曼编码表
        getCodes(rootNode);
        // 获得压缩后的字节数组
        return zip(bytes);
    }

    /**
     * 压缩
     */
    public static byte[] zip(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        // 将数组转成赫夫曼编码对应的字符串
        for (byte b : bytes) {
            stringBuilder.append(HUFFMANCODES.get(b));
        }

        // 统计byte[] huffmanCodeBytes的长度
        int len = (stringBuilder.length() + 7) / 8;
        // 压缩后的数组
        byte[] huffmanCodeBytes = new byte[len];

        // 用来记录是第几个字节
        int index = 0;
        // 每8位是一个byte
        for (int i = 0; i < stringBuilder.length(); i += 8) {
            String strByte;
            if (i + 8 >= stringBuilder.length()) {
                strByte = stringBuilder.substring(i);
            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }
            // 将strByte转换成字节数组
            huffmanCodeBytes[index++] = (byte) Integer.parseInt(strByte,2);
        }
        return huffmanCodeBytes;
    }

    /**
     * 存储路径的中间变量
     */
    private static StringBuilder STRINGBUILDER = new StringBuilder();

    /**
     * 生成赫夫曼编码
     */
    public static Map<Byte, String> getCodes(HuffmanTreeNode rootNode) {
        if (rootNode == null) {
            return null;
        }
        getCodes(rootNode.getLeft(),"0",STRINGBUILDER);
        getCodes(rootNode.getRight(),"1",STRINGBUILDER);
        return HUFFMANCODES;
    }

    /**
     * 生成赫夫曼编码，将赫夫曼编码对照信息放入map中
     * @param node 传入节点
     * @param code 路径，0是左，1是右
     * @param stringBuilder 用于拼接路径
     */
    public static void getCodes(HuffmanTreeNode node, String code, StringBuilder stringBuilder) {
        StringBuilder builder = new StringBuilder(stringBuilder);
        builder.append(code);
        if (node != null) {
            // 判断当前是否为叶子节点
            if (node.getData() != null) {
                HUFFMANCODES.put(node.getData(), builder.toString());
            } else {
                // 递归处理
                getCodes(node.getLeft(), "0", builder);
                getCodes(node.getRight(), "1", builder);
            }
        }
    }

    /**
     * 获取nodes
     */
    public static List<HuffmanTreeNode> getNodes(byte[] bytes) {
        // 新建一个map来计数
        Map<Byte, Integer> map = new HashMap<>();
        for (byte b : bytes) {
            map.merge(b, 1, Integer::sum);
        }

        // 根据map建立nodes
        List<HuffmanTreeNode> nodes = new ArrayList<>();
        map.forEach((data, weight) -> nodes.add(new HuffmanTreeNode(data, weight)));

        return nodes;
    }

    /**
     * 生成赫夫曼树
     */
    public static HuffmanTreeNode createHuffmanTree(List<HuffmanTreeNode> nodes) {

        while (nodes.size() > 1) {
            Collections.sort(nodes);
            // 取出最小的两棵二叉树建立新的二叉树
            HuffmanTreeNode leftNode = nodes.get(0);
            HuffmanTreeNode rightNode = nodes.get(1);
            HuffmanTreeNode parentNode = new HuffmanTreeNode(null, leftNode.getWeight() + rightNode.getWeight());
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
    //--------------------------------编码--------------------------------
}
