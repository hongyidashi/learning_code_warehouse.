package com.hl.interview._03concurrent._02atomic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 描述: 引用原子类测试
 * 作者: panhongtong
 * 创建时间: 2020-06-04 22:31
 **/
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<Person> ar = new AtomicReference<>();
        Person person = new Person("大福", 3);
        ar.set(person);
        Person updatePerson = new Person("断腿少女", 18);
        Person otherPerson = new Person("GDX", 22);
//        ar.compareAndSet(person, otherPerson);
        ar.compareAndSet(person, updatePerson);
        System.out.println(ar.get());
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Person {
    private String name;
    private Integer age;
}