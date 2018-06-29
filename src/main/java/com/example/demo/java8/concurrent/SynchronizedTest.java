package com.example.demo.java8.concurrent;

import com.example.demo.util.SleepUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/6/29
 */
public class SynchronizedTest {

    private static final int NUM_INCREMENTS = 1000000;

    private static int count = 0;

    public static void main(String[] args) {
        testSyncIncrement();
        //没有同步的计算结果会有误差
        testNonSyncIncrement();
        testSyncIncrementClass();
    }

    private static void testSyncIncrement() {
        count = 0;

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> executor.submit(SynchronizedTest::incrementSync));

        SleepUtils.stop(executor);

        System.out.println("   Sync: " + count);
    }

    private static void testNonSyncIncrement() {
        count = 0;

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> executor.submit(SynchronizedTest::increment));

        SleepUtils.stop(executor);

        System.out.println("NonSync: " + count);
    }

    private static void testSyncIncrementClass() {
        count = 0;

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> executor.submit(SynchronizedTest::incrementSyncClass));

        SleepUtils.stop(executor);

        System.out.println("SyncClass: " + count);
    }

    private static synchronized void incrementSync() {
        count = count + 1;
    }

    private static void increment() {
        count = count + 1;
    }

    private static void incrementSyncClass() {
        synchronized (SynchronizedTest.class) {
            count = count + 1;
        }
    }
}
