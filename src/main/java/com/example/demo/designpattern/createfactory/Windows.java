package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public abstract class Windows implements Product{

    @Override
    public Production production(){
        return new Hand();
    }

    @Override
    public abstract float price();
}
