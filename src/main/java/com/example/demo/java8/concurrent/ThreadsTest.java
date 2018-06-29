package com.example.demo.java8.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/6/29
 */
public class ThreadsTest {
    public static void main(String[] args) {
        test1();
    }


    private static void test1() {
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };

        runnable.run();

        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println("Done!");
    }
}
