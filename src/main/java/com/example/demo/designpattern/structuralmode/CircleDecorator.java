package com.example.demo.designpattern.structuralmode;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class CircleDecorator implements Decorator {
    @Override
    public void draw() {
        System.out.println("Decorator: CircleDecorator");
    }
}
