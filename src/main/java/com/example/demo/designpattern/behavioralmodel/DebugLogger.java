package com.example.demo.designpattern.behavioralmodel;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/9/11
 */
public class DebugLogger extends AbstractLogger {

    public DebugLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Debug::Logger: " + message);
    }
}