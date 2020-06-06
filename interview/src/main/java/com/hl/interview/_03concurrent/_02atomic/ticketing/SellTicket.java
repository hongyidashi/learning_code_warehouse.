package com.hl.interview._03concurrent._02atomic.ticketing;

/**
 * 描述: 卖票
 * 作者: panhongtong
 * 创建时间: 2020-06-05 22:37
 **/
public class SellTicket {
    public static void main(String[] args) throws InterruptedException {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Thread t1 = new Thread(ticket1);
        Thread t2 = new Thread(ticket2);
        t1.start();
        t2.start();
        Thread.sleep(1000);
        System.out.println("库存："+Ticket.num.get());
    }
}
