package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class PrototypeTest {

    public static void main(String[] args) {
        PrototypeCache.loadCache();
        PrototypeOne prototypeOne = (PrototypeOne) PrototypeCache.getProperType("1");
        System.out.println("Prototype : " + prototypeOne.getType());
        PrototypeTwo prototypeTwo = (PrototypeTwo) PrototypeCache.getProperType("2");
        System.out.println("Prototype : " + prototypeTwo.getType());
    }
}
