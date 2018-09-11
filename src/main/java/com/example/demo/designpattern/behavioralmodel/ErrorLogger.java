package com.example.demo.designpattern.behavioralmodel;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/9/11
 */
public class ErrorLogger extends AbstractLogger {

    public ErrorLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Error Console::Logger: " + message);
    }
}