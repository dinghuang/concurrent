package com.example.demo.designpattern.behavioralmodel;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/9/11
 */
public class ConsoleLogger extends AbstractLogger {

    public ConsoleLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Standard Console::Logger: " + message);
    }
}
