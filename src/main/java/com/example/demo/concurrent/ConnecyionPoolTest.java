package com.example.demo.concurrent;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程连接范例
 *
 * @author dinghuang123@gmail.com
 */
public class ConnecyionPoolTest {
    static ConnectionPool pool = new ConnectionPool(10);
    //保证所有ConnectiobnRunner能够同时开始
    //一个同步帮助，允许一个或多个线程等待，直到在其他线程中执行的一组操作完成。
    static CountDownLatch start = new CountDownLatch(1);
    //main线程将会等待所有ConnectionRunner结束后才能继续执行
    static CountDownLatch end;

    public static void main(String[] args) throws Exception {
        //线程数量，可以修改线程数量进行观察
        int threadCount = 1000;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        //减少门闩计数，如果计数达到零，释放所有等待的线程。
        start.countDown();
        //导致当前线程等待直到锁存到0为止，除非线程是被中断的。
        end.await();
        System.out.println("total invoke:" + (threadCount * count));
        System.out.println("got connection:" + got);
        System.out.println("not go connection:" + notGot);
    }

    private static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                //导致当前线程等待直到锁存到0为止，除非线程是被中断的。
                start.await();
            } catch (Exception ex) {
            }
            while (count > 0) {
                try {
                    //从线程池中获取连接，如果1000ms内无法获取到，将会返回null
                    //分别统计连接获取的数量got和未获取到的数量notGot
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception ex) {
                } finally {
                    count--;
                }
            }
            //减少门闩计数，如果计数达到零，释放所有等待的线程。
            end.countDown();
        }
    }
}
