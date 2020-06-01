package com.hl.interview._01basis._07datatype;

/**
 * 描述：基本数据类型和包装类测试
 * 作者: panhongtong
 * 创建时间: 2020-05-31 21:26
 **/
public class DataTypeDemo {
    public static void main(String[] args) {
        Integer a = new Integer(4);
        // 内部重写了equals方法，比较的是值
        /*
        public boolean equals(Object obj) {
            if (obj instanceof Integer) {
                return value == ((Integer)obj).intValue();
            }
            return false;
        }
        */
        a.equals(6);

    }
}
