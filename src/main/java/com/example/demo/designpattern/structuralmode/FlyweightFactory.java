package com.example.demo.designpattern.structuralmode;

import java.util.HashMap;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class FlyweightFactory {

    private static final HashMap<String, Flyweight> circleMap = new HashMap<>();

    public static Flyweight getCircle(String color) {
        CircleFlyweight circle = (CircleFlyweight) circleMap.get(color);

        if (circle == null) {
            circle = new CircleFlyweight(color);
            circleMap.put(color, circle);
            System.out.println("Creating circle of color : " + color);
        }
        return circle;
    }
}
