package com.example.demo.java8.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * java8线程练习
 *
 * @author dinghuang123@gmail.com
 * @since 2018/6/11
 */
public class ExecutorsTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        //executor.awaitTermination(5, TimeUnit.SECONDS) 超过等待终结时间5s，线程interrupt异常
//        test1(3);
        //用future获取线程执行成功回调
//        test1();
        //线程执行结束命令后不能用future
//        test2();
        //future可以设置等待时长返回,超时报异常
//        test3();
        //执行批量任务
//        test4();
        //设置批量任务执行时间
//        test5();
        //创建并执行一个周期性任务在延迟后，直到被打断
//        test6();
        //创建并执行一个周期性任务固定频率开始，直到被打断
//        test7();
        //任务线程回调
//        test8();
    }

    private static void test1(long seconds) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(seconds);
                String name = Thread.currentThread().getName();
                System.out.println("task finished: " + name);
            } catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        });
        stop(executor);
    }

    static void stop(ExecutorService executor) {
        try {
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("termination interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("killing non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    private static void test3() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        });
        System.out.println(future.get(3, TimeUnit.SECONDS));
    }

    private static void test2() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        });
        executor.shutdownNow();
        future.get();
    }

    private static void test1() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(() -> {
            try {
                System.out.println("XX");
                TimeUnit.SECONDS.sleep(5);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        });
        System.out.println("future done: " + future.isDone());
        Integer result = future.get();
        System.out.println("future done: " + future.isDone());
        System.out.print("result: " + result);
        executor.shutdownNow();
    }

    private static void test5() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> callables = Arrays.asList(
                callable("task1", 4),
                callable("task2", 3),
                callable("task3", 2));
        String result = executor.invokeAny(callables);
        System.out.println(result);
        executor.shutdown();
    }

    private static Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

    private static void test4() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3");
        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
        executor.shutdown();
    }

    private static void test6() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            } catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };
        //创建并执行一个周期性动作，该动作在给定的初始延迟之后首先启用，然后
        // 在一个执行终止到下一个执行开始之间的给定延迟。如果任务的任何执行遇到异常，
        // 则禁止后续执行。否则，任务将仅通过执行方的取消或终止而终止。
        //initialDelay 延迟第一次执行的时间 delay 执行和开始下一个终止之间的延迟
        //unit 初始延迟和延迟参数的时间单位
        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(5);
        executor.shutdown();
    }

    private static void test7() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    private static void test8() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        int delay = 3;
        ScheduledFuture<?> future = executor.schedule(task, delay, TimeUnit.SECONDS);
        TimeUnit.MILLISECONDS.sleep(1337);
        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("Remaining Delay: %sms\n", remainingDelay);
    }
}
