package com.example.demo.lambda;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

import static java.lang.System.out;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/5/25
 */
public class ArrayStream {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        // 统计并行执行list的线程
        Set<Thread> threadSet = new CopyOnWriteArraySet<>();
        Long time = System.currentTimeMillis();
        // 并行执行(跟直接使用forEach比起来，用到了ForkJoinPool线程池，该线程池的线程数量跟cpu线程数量一致，并行执行，但是
        // 没有直接使用forEach快)
        list.parallelStream().forEach(integer -> {
            Thread thread = Thread.currentThread();
            // 统计并行执行list的线程
            threadSet.add(thread);
        });
        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - time);
        System.out.println("threadSet一共有" + threadSet.size() + "个线程");
        System.out.println("系统一个有" + Runtime.getRuntime().availableProcessors() + "个cpu");
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list1.add(i);
            list2.add(i);
        }
        Set<Thread> threadSetTwo = new CopyOnWriteArraySet<>();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread threadA = new Thread(() -> {
            list1.parallelStream().forEach(integer -> {
                Thread thread = Thread.currentThread();
                System.out.println("list1" + thread);
                threadSetTwo.add(thread);
            });
            countDownLatch.countDown();
        });
        Thread threadB = new Thread(() -> {
            list2.parallelStream().forEach(integer -> {
                Thread thread = Thread.currentThread();
                System.out.println("list2" + thread);
                threadSetTwo.add(thread);
            });
            countDownLatch.countDown();
        });
        threadA.start();
        threadB.start();
        countDownLatch.await();
        System.out.println("---------------------------");
        System.out.println(threadSetTwo);
        System.out.println("threadSetTwo一共有" + threadSetTwo.size() + "个线程");
        System.out.println("系统一个有" + Runtime.getRuntime().availableProcessors() + "个cpu");
    }

    public static void sortSequence() {
        //有序
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        numbers.parallelStream().forEachOrdered(out::print);
    }

    public static void sortNotSequence() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        numbers.parallelStream().forEach(out::print);
    }

    public static String query(String q, List<String> engines) {
        Optional<String> result = engines.stream().parallel().map(base -> {
            String url = base + q;
            return url;
        }).findAny();
        return result.get();
    }


}
