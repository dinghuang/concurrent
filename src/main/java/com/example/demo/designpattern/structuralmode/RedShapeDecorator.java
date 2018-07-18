package com.example.demo.designpattern.structuralmode;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(Decorator decorator) {
        super(decorator);
    }

    @Override
    public void draw() {
        decorator.draw();
        setRedBorder(decorator);
    }

    private void setRedBorder(Decorator decorator) {
        System.out.println("Border Color: Red");
    }
}
