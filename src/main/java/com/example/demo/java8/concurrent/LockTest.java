package com.example.demo.java8.concurrent;

import com.example.demo.util.SleepUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/6/15
 */
public class LockTest {

    private static final int NUM_INCREMENTS = 10000;

    private static ReentrantLock lock = new ReentrantLock();

    private static int count = 0;

    private static void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        //可重入锁,线程安全的自增到10000
//        testLock();
        //可重入锁,判断锁是否由当前线程持有
//        testLock2();
        //读写锁
//        testLock3();
        //StampedLock控制锁有三种模式（写，读，乐观读）
        // ，一个StampedLock状态是由版本和模式两个部分组成，锁获取方法返回一个数字作为票据stamp，
        // 它用相应的锁状态表示并控制访问，数字0表示没有写锁被授权访问。在读锁上分为悲观锁和乐观锁
//        test5();
        //StampedLock的乐观读锁
//        test6();
        //StampedLock读锁转换写锁
        test7();
    }

    private static void test7() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        StampedLock stampedLock = new StampedLock();

        executorService.submit(() -> {
            long stamp = stampedLock.readLock();
            try {
                if (count == 0) {
                    stamp = stampedLock.tryConvertToWriteLock(stamp);
                    if (stamp == 0L) {
                        System.out.println("Could not convert to write lock");
                        stamp = stampedLock.writeLock();
                    }
                    count = 23;
                }
                System.out.println(count);
            } finally {
                stampedLock.unlock(stamp);
            }
        });

        SleepUtils.stop(executorService);
    }

    private static void test6() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        executorService.submit(() -> {
            long stamp = lock.writeLock();
            try {
                System.out.println("Write Lock acquired");
                SleepUtils.second(2);
            } finally {
                lock.unlock(stamp);
                System.out.println("Write done");
            }
        });

        executorService.execute(() -> {
            long stamp = lock.tryOptimisticRead();
            try {
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                SleepUtils.second(1);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                SleepUtils.second(2);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
            } finally {
                lock.unlock(stamp);
            }
        });

        SleepUtils.stop(executorService);
    }

    private static void testLock() {
        count = 0;
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> executor.submit(LockTest::increment));
        SleepUtils.stop(executor);
        System.out.println(count);
    }

    private static void testLock2() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLock lock2 = new ReentrantLock();
        executor.submit(() -> {
            lock2.lock();
            try {
                SleepUtils.second(1);
            } finally {
                lock2.unlock();
            }
        });
        executor.submit(() -> {
            System.out.println("Locked: " + lock2.isLocked());
            //判断锁是否由当前线程持有
            System.out.println("Held by me: " + lock2.isHeldByCurrentThread());
            boolean locked = lock2.tryLock();
            System.out.println("Lock acquired: " + locked);
        });
        SleepUtils.stop(executor);
    }

    private static void testLock3() {
        //创建并发访问的账户
        MyCount myCount = new MyCount("95599200901215522", 10000);
        //创建一个锁对象
        ReadWriteLock lock = new ReentrantReadWriteLock(false);
        //创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);
        //创建一些并发访问用户，一个信用卡，存的存，取的取，好热闹啊
        User u1 = new User("张三", myCount, -4000, lock, false);
        User u2 = new User("张三他爹", myCount, 6000, lock, false);
        User u3 = new User("张三他弟", myCount, -8000, lock, false);
        User u4 = new User("张三", myCount, 800, lock, false);
        User u5 = new User("张三他爹", myCount, 0, lock, true);
        //在线程池中执行各个用户的操作
        pool.execute(u1);
        pool.execute(u2);
        pool.execute(u3);
        pool.execute(u4);
        pool.execute(u5);
        //关闭线程池
        pool.shutdown();
    }

    public static void test5() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        StampedLock lock = new StampedLock();
        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                SleepUtils.second(1);
                map.put("foo", "bar");
            } finally {
                lock.unlockWrite(stamp);
            }
        });
        Runnable readTask = () -> {
            long stamp = lock.readLock();
            try {
                System.out.println(map.get("foo"));
                SleepUtils.second(1);
            } finally {
                lock.unlockRead(stamp);
            }
        };
        executor.submit(readTask);
        executor.submit(readTask);
        SleepUtils.stop(executor);
    }

    static class User implements Runnable {
        private String name;                 //用户名
        private MyCount myCount;         //所要操作的账户
        private int iocash;                 //操作的金额，当然有正负之分了
        private ReadWriteLock myLock;                 //执行操作所需的锁对象
        private boolean ischeck;         //是否查询

        User(String name, MyCount myCount, int iocash, ReadWriteLock myLock, boolean ischeck) {
            this.name = name;
            this.myCount = myCount;
            this.iocash = iocash;
            this.myLock = myLock;
            this.ischeck = ischeck;
        }

        public void run() {
            if (ischeck) {
                //获取读锁
                myLock.readLock().lock();
                System.out.println("读：" + name + "正在查询" + myCount + "账户，当前金额为" + myCount.getCash());
                //释放读锁
                myLock.readLock().unlock();
            } else {
                //获取写锁
                myLock.writeLock().lock();
                //执行现金业务
                System.out.println("写：" + name + "正在操作" + myCount + "账户，金额为" + iocash + "，当前金额为" + myCount.getCash());
                myCount.setCash(myCount.getCash() + iocash);
                System.out.println("写：" + name + "操作" + myCount + "账户成功，金额为" + iocash + "，当前金额为" + myCount.getCash());
                //释放写锁
                myLock.writeLock().unlock();
            }
        }
    }

    static class MyCount {
        private String oid;         //账号
        private int cash;             //账户余额

        MyCount(String oid, int cash) {
            this.oid = oid;
            this.cash = cash;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public int getCash() {
            return cash;
        }

        public void setCash(int cash) {
            this.cash = cash;
        }

        @Override
        public String toString() {
            return "MyCount{" +
                    "oid='" + oid + '\'' +
                    ", cash=" + cash +
                    '}';
        }
    }
}
