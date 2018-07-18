package com.example.demo.designpattern.structuralmode;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class RectangleFacade implements Facade {
    @Override
    public void draw() {
        System.out.println("Rectangle::draw()");
    }
}
