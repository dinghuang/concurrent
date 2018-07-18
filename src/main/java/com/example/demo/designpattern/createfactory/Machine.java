package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class Machine implements Production {
    @Override
    public String production() {
        return "Machine";
    }
}
