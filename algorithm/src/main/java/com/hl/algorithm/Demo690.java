package com.hl.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * 给定一个保存员工信息的数据结构，它包含了员工唯一的id，重要度 和 直系下属的id。
 * 比如，员工1是员工2的领导，员工2是员工3的领导。他们相应的重要度为15, 10, 5。那么员工1的数据结构是[1, 15, [2]]，员工2的数据结构是[2, 10, [3]]，员工3的数据结构是[3, 5, []]。注意虽然员工3也是员工1的一个下属，但是由于并不是直系下属，因此没有体现在员工1的数据结构中。
 * 现在输入一个公司的所有员工信息，以及单个员工id，返回这个员工和他所有下属的重要度之和。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/employee-importance
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-27 10:15
 **/
public class Demo690 {

    public int getImportance(List<Employee> employees, int id) {
        Map<Integer, Employee> map = new HashMap<>(employees.size());
        for (Employee employee : employees) {
            map.put(employee.id, employee);
        }

        return getImportance(map, map.get(id).subordinates) + map.get(id).importance;
    }

    public int getImportance(Map<Integer, Employee> map, List<Integer> ids) {
        int sum = 0;
        for (Integer id : ids) {
            sum += map.get(id).importance;
            if (map.get(id).subordinates.size() != 0) {
                sum += getImportance(map, map.get(id).subordinates);
            }
        }
        return sum;
    }


}

class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
}
