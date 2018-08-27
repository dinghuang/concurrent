package com.example.demo.concurrent;

import com.example.demo.util.SleepUtils;

import java.util.concurrent.TimeUnit;

/**
 * 对两个线程分别进行终端操作，观察二者的中断标识
 * 结果：抛出InterruptedException的线程SleepThread，其中断标识被清除了
 * Created by dinghuang on 2018/2/24.
 */
public class Interrupted {
    static class SleepRunner implements Runnable {

        public static void main(String[] args) throws Exception {
            //sleepThread不停的尝试睡眠
            Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
            sleepThread.setDaemon(true);
            //busyThread不停的运行
            Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
            busyThread.setDaemon(true);
            busyThread.start();
            sleepThread.start();
            //休眠5秒，让sleepThread和busyThread充分运行
            TimeUnit.SECONDS.sleep(5);
            sleepThread.interrupt();
            busyThread.interrupt();
            System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
            System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
            //防止sleepThread和busyThread立刻退出
            SleepUtils.second(2);
        }

        @Override
        public void run() {
            while (true) {
                SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable {

        @Override
        public void run() {
            while (true) {
            }
        }
    }
}
