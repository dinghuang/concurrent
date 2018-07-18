package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class PrototypeTwo extends Prototype {

    public PrototypeTwo() {
        type = "prototypeTwo";
    }

    @Override
    void operation() {
        System.out.println("Inside PrototypeTwo::draw() method.");
    }
}
