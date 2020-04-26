package com.hl.hashtab;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @Auther: panhongtong
 * @Date: 2020/4/24 11:01
 * @Description: 数据结构-哈希
 */
public class HashTabDemo {
    public static void main(String[] args) {
        HashTab hashTab = new HashTab(7);
        hashTab.add(new Emp(1, "断腿少女"));
        hashTab.add(new Emp(7, "断腿少女"));
        hashTab.add(new Emp(14, "断腿少女"));
        hashTab.add(new Emp(21, "断腿少女"));
        hashTab.list();
        System.out.println(hashTab.findEmpById(14));
    }
}

/**
 * 哈希表
 */
@Data
class HashTab {
    private EmpLinkedList[] empLinkedListArray;
    private int size;

    public HashTab(int size) {
        this.size = size;
        // 初始化链表empLinkedListArray
        empLinkedListArray = new EmpLinkedList[size];
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    /**
     * 添加一个员工
     */
    public void add(Emp emp) {
        int no = hashFunc(emp.getId());
        empLinkedListArray[no].add(emp);
    }

    /**
     * 遍历hashtab
     */
    public void list() {
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list(i);
        }
    }

    /**
     * 根据ID查找员工
     */
    public Emp findEmpById(int id) {
        int no = hashFunc(id);
        return empLinkedListArray[no].finEmpById(id);
    }

    /**
     * 编写一个散列函数
     */
    private int hashFunc(int id) {
        return id & size - 1;
    }
}

/**
 * 员工类
 */
@Setter
@Getter
class Emp {
    private int id;
    private String name;
    private Emp nextEmp;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

/**
 * 链表
 */
@Data
class EmpLinkedList {
    /**
     * 头结点
     */
    private Emp head;

    /**
     * 添加一个员工
     */
    public void add(Emp emp) {
        if (Objects.isNull(head)) {
            head = emp;
            return;
        }
        Emp curEmp = head;
        while (curEmp.getNextEmp() != null) {
            curEmp = curEmp.getNextEmp();
        }
        curEmp.setNextEmp(emp);
    }

    /**
     * 遍历链表
     */
    public void list(int i) {
        if (head == null) {
            System.out.println("链表" + i + "为空");
            return;
        }
        System.out.println("链表"+ i +"信息为：");
        Emp emp = head;
        while (true) {
            System.out.println("Emp------>id:" + emp.getId() + "  name:" + emp.getName());
            if (emp.getNextEmp() == null) {
                break;
            }
            emp = emp.getNextEmp();
        }
    }

    /**
     * 根据ID查找
     */
    public Emp finEmpById(int id) {
        if (head == null) {
            return null;
        }
        Emp curEmp = head;
        while (true) {
            if (curEmp.getId() == id) {
                return curEmp;
            }

            if (curEmp.getNextEmp() == null) {
                return null;
            }

            curEmp = curEmp.getNextEmp();
        }
    }
}
