package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class CarFactory extends AbstractFactory{
    @Override
    public Passengers getPassengers(String user) {
        return null;
    }

    @Override
    public Car getCar(String type) {
        if (type == null) {
            return null;
        }
        if (type.equals("litterCar")) {
            return new LitterCar();
        } else if (type.equals("ExpensiveCar")) {
            return new ExpensiveCar();
        }
        return null;
    }
}
