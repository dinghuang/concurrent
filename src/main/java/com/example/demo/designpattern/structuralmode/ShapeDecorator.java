package com.example.demo.designpattern.structuralmode;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public abstract class ShapeDecorator implements Decorator{

    protected Decorator decorator ;

    public ShapeDecorator(Decorator decorator){
        this.decorator = decorator;
    }
    @Override
    public void draw() {
        decorator.draw();
    }
}
