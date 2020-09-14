package com.hl.basis.singleton;

/**
 * 描述: 单例防漏洞攻击
 * 作者: panhongtong
 * 创建时间: 2020-09-09 16:07
 **/
public class MySingleton {

    private static boolean flag = false;

    public static MySingleton mySingleton = new MySingleton();

    private MySingleton() {
        if (flag == false) {
            flag = !flag;
        } else {
            throw new RuntimeException("诶嘿，小东西，想反射爷的类？");
        }
    }

    public static MySingleton getInst() {
        return mySingleton;
    }

    String name = "大福";

    @Override
    public String toString() {
        return "MySingleton{" +
                "name='" + name + '\'' +
                '}';
    }
}
