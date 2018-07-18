package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class PassengerFactory extends AbstractFactory {
    @Override
    public Passengers getPassengers(String user) {
        if (user == null) {
            return null;
        }
        if (user.equals("man")) {
            return new Man();
        } else if (user.equals("woman")) {
            return new Woman();
        }
        return null;
    }

    @Override
    public Car getCar(String car) {
        return null;
    }
}
