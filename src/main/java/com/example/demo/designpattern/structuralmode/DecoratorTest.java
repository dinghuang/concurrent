package com.example.demo.designpattern.structuralmode;

import static java.lang.System.*;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class DecoratorTest {

    public static void main(String[] args) {
        Decorator circle = new CircleDecorator();

        Decorator redCircle = new RedShapeDecorator(new CircleDecorator());

        Decorator redRectangle = new RedShapeDecorator(new Rectangle());

        System.out.println("Circle with normal border");
        circle.draw();

        out.println("\nCircle of red border");
        redCircle.draw();

        out.println("\nRectangle of red border");
        redRectangle.draw();
    }


}
