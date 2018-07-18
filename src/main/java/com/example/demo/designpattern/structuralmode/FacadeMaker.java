package com.example.demo.designpattern.structuralmode;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class FacadeMaker {

    private Facade rectangleFacade;

    private Facade squareFacade;

    public FacadeMaker(){
        rectangleFacade = new RectangleFacade();
        squareFacade = new SquareFacade();
    }

    public void drawSquare(){
        squareFacade.draw();
    }
    public void drawRectangle(){
        rectangleFacade.draw();
    }

}
