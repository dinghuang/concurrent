package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class MacSystem extends Mac {
    @Override
    public String name() {
        return "macSystem";
    }

    @Override
    public float price() {
        return 30.0f;
    }
}
