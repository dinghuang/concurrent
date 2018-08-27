package com.example.demo.concurrent;


/**
 * 同步程序的顺序一致性结果
 * Created by dinghuang on 2018/2/23.
 */
public class SynchronizedExample {
    static int a = 0;
    static boolean flag = false;

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    writer();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    reader();
                }
            }
        });
        t2.start();
        t.start();
    }

    public static synchronized void writer() {
        a = 1;
        flag = true;
    }

    public static synchronized void reader() {
        if (flag) {
            int i = a;
        }
    }
}
