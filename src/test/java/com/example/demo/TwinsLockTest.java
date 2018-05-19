package com.example.demo;

import com.example.demo.lock.TwinsLock;
import com.example.demo.util.SleepUtils;
import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * 定义了工作线程Worker，该线程在执行过程中获取锁，
 * 当获取锁之后是当前线程睡眠1秒（不释放锁），随后
 * 打印当前线程名称，再睡眠1秒释放锁
 *
 * @author dinghuang123@gmail.com
 * @since 2018/5/19
 */
public class TwinsLockTest {

    @Test
    public void test() {
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        //启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        //每隔1秒换行
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println();
        }
    }
}
