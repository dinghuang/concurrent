package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public abstract class AbstractFactory {
    public abstract Passengers getPassengers(String user);
    public abstract Car getCar(String car);
}
