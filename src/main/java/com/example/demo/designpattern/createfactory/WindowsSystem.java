package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class WindowsSystem extends Windows{
    @Override
    public String name() {
        return "windowsSystem";
    }

    @Override
    public float price() {
        return 25.0f;
    }
}
