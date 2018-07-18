package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class FactoryProducer {
    public static AbstractFactory getFactory(String choice) {
        if (choice.equalsIgnoreCase("Car")) {
            return new CarFactory();
        } else if (choice.equalsIgnoreCase("Passenger")) {
            return new PassengerFactory();
        }
        return null;
    }
}
