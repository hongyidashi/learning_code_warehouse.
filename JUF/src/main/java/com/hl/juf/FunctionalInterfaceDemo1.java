package com.hl.juf;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Auther: panhongtong
 * @Date: 2020/3/26 15:42
 */
public class FunctionalInterfaceDemo1 {
    public static void main(String[] args) {

        //方法函数 一个参数一个返回值
        Function<String,Integer> function = str-> 10086;
        System.out.println(function.apply(""));

        //断言函数 一个请求参数返回Boolen
        Predicate<String> predicate = str -> str.isEmpty();
        System.out.println(predicate.test("大福"));

        //消费函数 一个请求参数无返回值
        Consumer<String> consumer = System.out::println;
        consumer.accept("MDZZ");

        //供给函数 无请求参数有返回值
        Supplier<String> supplier = () -> "断腿少女";
        System.out.println(supplier.get());
    }

}
