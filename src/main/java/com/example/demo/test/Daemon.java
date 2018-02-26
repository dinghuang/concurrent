package com.example.demo.test;

import com.example.demo.util.SleepUtils;

/**
 * Daemon线程是一种支持型线程，但是在Java虚拟机退出时Daemon线程中的finally块不一定会执行
 * Created by dinghuang on 2018/2/24.
 */
public class Daemon {
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable {

        @Override
        public void run() {
            try {
                SleepUtils.second(10);
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
