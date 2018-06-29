package com.example.demo.java8.concurrent;

import com.example.demo.util.SleepUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

/**
 * 线程中的累加器
 *
 * @author dinghuang123@gmail.com
 * @since 2018/6/29
 */
public class AccumulatorTest {

    private static final int NUM_INCREMENTS = 10000;

    private static LongAdder adder = new LongAdder();

    public static void main(String[] args) {
        //根据给定函数进行累加
//        test1();
        //累加
//        test2();
        //累加
//        test3();

    }

    private static void test3() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> executor.submit(adder::increment));

        SleepUtils.stop(executor);

        System.out.format("Increment: Expected=%d; Is=%d\n", NUM_INCREMENTS, adder.sumThenReset());
    }

    private static void test2() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> {
                    executor.submit(() -> adder.add(2));
                    System.out.println(i);
                });

        SleepUtils.stop(executor);

        System.out.format("Add: %d\n", adder.sumThenReset());
    }

    private static void test1() {
        LongBinaryOperator op = (x, y) -> x + y;
        //x初始值值为2
        LongAccumulator accumulator = new LongAccumulator(op, 2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        //每次计算出来的x值为下一次计算的y值
        IntStream.range(0, 10).forEach(i -> {
            executorService.submit(() -> accumulator.accumulate(i));
            System.out.println(i);
        });
        SleepUtils.stop(executorService);
        System.out.format("Add: %d\n", accumulator.getThenReset());
    }

}
