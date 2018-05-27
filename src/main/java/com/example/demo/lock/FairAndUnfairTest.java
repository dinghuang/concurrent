package com.example.demo.lock;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 观察公平锁和非公平锁在获取锁的区别
 *
 * @author dinghuang123@gmail.com
 * @since 2018/5/27
 */
public class FairAndUnfairTest {
    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unFairLock = new ReentrantLock2(false);

    public static void main(String[] args){
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(unFairLock));
            thread.setName(i + "");
            thread.start();
        }
    }

    private static class Job extends Thread {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
//                    Thread.sleep(1000);
                    System.out.print("获取锁的当前线程[" + Thread.currentThread().getName() + "]");
                    System.out.print("等待队列中的线程[");
                    ((ReentrantLock2) lock).getQueueThreads();
                    System.out.println("]");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }

    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public void getQueueThreads() {
            List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            arrayList.forEach(thread -> System.out.print(thread.getName()));
        }
    }
}
