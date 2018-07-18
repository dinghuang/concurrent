package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class PrototypeOne extends Prototype {

    public PrototypeOne(){
        type = "prototypeOne";
    }

    @Override
    void operation() {
        System.out.println("Inside PrototypeOne::draw() method.");
    }
}
