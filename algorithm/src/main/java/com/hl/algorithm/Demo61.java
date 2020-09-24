package com.hl.algorithm;

/**
 * 描述:
 *
 * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-24 20:37
 **/
public class Demo61 {

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return null;
        }

        if (head.next == null) {
            return head;
        }

        // 旧的尾部节点
        ListNode oldTail = head;

        // 元素个数
        int n;
        // 形成闭环
        for (n = 1; oldTail.next != null; n++) {
            oldTail = oldTail.next;
        }
        oldTail.next = head;

        ListNode newTail = head;

        for (int i = 0; i < n - k % n - 1; i++) {
            newTail = newTail.next;
        }

        ListNode newHead = newTail.next;
        newTail.next = null;


        return newHead;
    }
}
