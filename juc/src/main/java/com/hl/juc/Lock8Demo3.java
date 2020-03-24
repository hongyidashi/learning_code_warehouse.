package com.hl.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Phone {

    static List<String> LIST = new ArrayList<>();

    public static synchronized void staticSendEamil() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sendEamil~~~~");
    }
    public synchronized void sendEamil() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sendEamil~~~~");
    }

    public static synchronized void staticSendSMS() {
        System.out.println("sendSMS~~~~");
    }
    public synchronized void sendSMS() {
        System.out.println("sendSMS~~~~");
    }

    public void sayHello() {
        System.out.println("Hello");
    }

    public void sayGoodBye() {
        synchronized (this) {
            System.out.println("goodbye~~~");
        }
    }
}

/**
 * @Auther: panhongtong
 * @Date: 2020/3/24 09:46
 * @Description: 8锁问题
 */
public class Lock8Demo3 {

    /**
     * 普通方法：
     * 普通方法锁的是该实例对象，同一个对象，一旦有线程进入了该类中带有synchronized的方法，
     * 其他所有带有synchronized的方法都会被锁住，
     * 简而言之就是所有带synchronized方法（包括方法里面带有synchronized）都受到影响
     *
     * 静态方法：
     * 静态方法锁的是类里static修饰的方法，只有带static的方法会被影响，static属性不受影响
     * 字节码里只有一个static块，锁的是这个static块
     */
    public static void main(String[] args) {
        Lock8Demo3 demo3 = new Lock8Demo3();
        //demo3.test1();
        //demo3.test2();
        //demo3.test3();
        //demo3.test4();
        //demo3.test5();
        //demo3.test6();
        //demo3.test7();
        //demo3.test8();
        demo3.test9();
    }

    public void test9() {
        Phone phone = new Phone();
        new Thread(phone::sendEamil).start();
        new Thread(()-> Phone.LIST.add("元素")).start();
        System.out.println(Phone.LIST);
    }

    /**
     * 两个手机，一个静态同步方法，一个普通方法，发邮件睡眠4秒
     * 结果：
     * sendSMS~~~~
     * sendEamil~~~~
     */
    public void test8() {
        Phone phone = new Phone();
        Phone phone1 = new Phone();
        new Thread(() -> phone.staticSendEamil()).start();
        new Thread(phone1::sendSMS).start();
    }

    /**
     * 一个手机，一个静态同步方法，一个普通方法，发邮件睡眠4秒
     * 结果：
     * sendSMS~~~~
     * sendEamil~~~~
     */
    public void test7() {
        Phone phone = new Phone();
        new Thread(() -> phone.staticSendEamil()).start();
        new Thread(phone::sendSMS).start();
    }

    /**
     * 一个手机，两个静态同步方法，发邮件睡眠4秒
     * 结果：
     * sendEamil~~~~
     * sendSMS~~~~
     */
    public void test6() {
        Phone phone = new Phone();
        Phone phone1 = new Phone();

        new Thread(() -> phone.staticSendEamil()).start();
        new Thread(() -> phone1.staticSendSMS()).start();
    }

    /**
     * 一个手机，两个静态同步方法，发邮件睡眠4秒
     * 结果：
     * sendEamil~~~~
     * sendSMS~~~~
     */
    public void test5() {
        new Thread(Phone::staticSendEamil).start();
        new Thread(Phone::staticSendSMS).start();
    }

    /**
     * 两个手机，发邮件睡眠4秒
     * 结果：
     * sendSMS~~~~
     * sendEamil~~~~
     */
    public void test4() {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(phone::sendEamil).start();
        new Thread(phone2::sendSMS).start();
    }

    /**
     * 新增普通sayHello方法，发邮件睡眠4秒
     * 结果：
     * Hello
     * sendEamil~~~~
     * sendSMS~~~~
     */
    public void test3() {
        Phone phone = new Phone();
        new Thread(phone::sendEamil).start();
        new Thread(phone::sendSMS).start();
        new Thread(phone::sayHello).start();
    }

    /**
     * 发邮件睡眠4秒
     * 结果：
     * sendEamil~~~~
     * sendSMS~~~~
     */
    public void test2() {
        Phone phone = new Phone();
        new Thread(phone::sendEamil).start();
        new Thread(phone::sendSMS).start();
    }

    /**
     * 标准访问
     * 结果：
     * sendEamil~~~~
     * sendSMS~~~~
     */
    public void test1() {
        Phone phone = new Phone();
        new Thread(phone::sendEamil).start();
        new Thread(phone::sendSMS).start();
    }
}
