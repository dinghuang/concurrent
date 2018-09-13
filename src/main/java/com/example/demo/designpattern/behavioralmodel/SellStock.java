package com.example.demo.designpattern.behavioralmodel;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/9/13
 */
public class SellStock implements Order {
    private Stock abcStock;

    public SellStock(Stock abcStock) {
        this.abcStock = abcStock;
    }

    @Override
    public void execute() {
        abcStock.sell();
    }
}
