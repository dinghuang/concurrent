package com.example.demo.designpattern.structuralmode;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class Employee {
    private String name;
    private String dept;
    private int salary;
    private List<Employee> subordinates;

    //构造函数
    public Employee(String name,String dept, int sal) {
        this.name = name;
        this.dept = dept;
        this.salary = sal;
        subordinates = new ArrayList<>();
    }

    public void add(Employee e) {
        subordinates.add(e);
    }

    public void remove(Employee e) {
        subordinates.remove(e);
    }

    public List<Employee> getSubordinates(){
        return subordinates;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("dept", dept)
                .add("salary", salary)
                .add("subordinates", subordinates)
                .toString();
    }
}
