package com.example.demo.proxy;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */
public class Printer {
    public void print(CallbackTest callback, String text) {
        System.out.println("正在打印 . . . ");
        try {
            Thread.currentThread();
            Thread.sleep(3000);
        } catch (Exception e) {
        }
        callback.printFinished("打印完成");
    }
}
