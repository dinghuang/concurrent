package com.example.demo.designpattern.structuralmode;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class FacadeTest {

    public static void main(String[] args) {
        FacadeMaker facadeMaker = new FacadeMaker();

        facadeMaker.drawRectangle();
        facadeMaker.drawSquare();
    }
}
