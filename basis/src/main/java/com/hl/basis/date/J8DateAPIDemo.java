package com.hl.basis.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @description: Java8时间API
 * @author: panhongtong
 * @create: 2020-04-19 09:17
 **/
public class J8DateAPIDemo {
    public static void main(String[] args) {
        //获取当前日期
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        //获取两天后的日期
        LocalDate localDate2 = LocalDate.now().plusDays(2);
        System.out.println(localDate2);

        //自定义日期
        LocalDate localDate1 = LocalDate.of(2020, 6, 21);
        System.out.println(localDate1);

        //获取当前时间，不含日期
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        LocalTime localTime1 = LocalTime.of(13, 55);
        System.out.println(localTime1);

        //获取三小时后/前的时间
        LocalTime localTime2 = LocalTime.now().plusHours(3);
        System.out.println(localTime2);
        LocalTime localTime3 = LocalTime.now().plusHours(-3);
        System.out.println(localTime3);


        //自定义多久后的时间
        LocalDate localDate3 = LocalDate.now().plus(-3, ChronoUnit.WEEKS);
        System.out.println(localDate3);

        //判断一个日期是否晚于另一个日期
        LocalTime time1 = LocalTime.now();
        LocalTime time2 = LocalTime.of(7, 50);
        System.out.println(time1.isAfter(time2));

        //获取当前时间戳
        Instant timestamp = Instant.now();
        System.out.println(timestamp);
        System.out.println(timestamp.toEpochMilli());

        //使用预定义的格式化工具去解析或格式化日期
        LocalDate parseDate = LocalDate.parse("20200330", DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(parseDate);

        //日期-字符串互相转换
        LocalDateTime date = LocalDateTime.now();
        //日期转字符串
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy/MM/ddHH:mm:ss");
        String str = date.format(format1);
        System.out.println("日期转换为字符串:" + str);
        //字符串转日期
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyy/MM/ddHH:mm:ss");
        LocalDate date2 = LocalDate.parse(str, format2);
        System.out.println("日期类型:" + date2);

    }
}
