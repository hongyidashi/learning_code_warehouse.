package com.hl.interview._01basis._02interface;

/**
 * 接口默认实现，Java8后开始支持
 * 只允许static、final变量，只要定义了变量，默认是static final
 */
public interface TestInterfaceDefault {

    default String testDefault() {
        return "默认";
    }

    //modifier 'static' is redundant for interface fields
    final Integer VALUE = 100;
    //Modifier 'static' is redundant for interface fields
    static String VARIABLE = "静态变量";

    //protected String proMethod();￿￿
}
