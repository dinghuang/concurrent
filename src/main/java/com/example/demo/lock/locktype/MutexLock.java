package com.example.demo.lock.locktype;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 互斥锁是阻塞锁，当某线程无法获取互斥锁时，该线程会被直接挂起，不再消耗CPU时间，当其他线程释放互斥锁后，操作系统会唤醒那个被挂起的线程。
 * <p>
 * 阻塞锁可以说是让线程进入阻塞状态进行等待，当获得相应的信号（唤醒，时间）时，才可以进入线程的准备就绪状态，
 * 准备就绪状态的所有线程，通过竞争进入运行状态。它的优势在于，阻塞的线程不会占用 CPU 时间， 不会导致 CPU 占用率过高，
 * 但进入时间以及恢复时间都要比自旋锁略慢。在竞争激烈的情况下阻塞锁的性能要明显高于自旋锁。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/8/26
 */
public class MutexLock {
    /**
     * JAVA中，能够进入/退出、阻塞状态或包含阻塞锁的方法有:
     * synchronized
     * ReentrantLock
     * Object.wait()/notify()
     * LockSupport.park()/unpart()(j.u.c经常使用)
     * 如果是多核处理器，如果预计线程等待锁的时间较长，至少比两次线程上下文切换的时间要长，建议使用互斥锁。
     * <p>
     * 可重入锁是一种特殊的互斥锁，它可以被同一个线程多次获取，而不会产生死锁。
     * 首先它是互斥锁：任意时刻，只有一个线程锁。即假设A线程已经获取了锁，在A线程释放这个锁之前，B线程是无法获取到这个锁的
     * ，B要获取这个锁就会进入阻塞状态。
     * 其次，它可以被同一个线程多次持有。即，假设A线程已经获取了这个锁，如果A线程在释放锁之前又一次请求获取这个锁，那么是能够获取成功的。
     * <pre>Java中的synchronized, ReentrantLock都是可重入锁。</pre>
     *
     * @param args args
     */
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        // 获取锁
        lock.lock();
        try {
            // access the resource protected by this lock
        } finally {
            // 释放锁
            lock.unlock();
        }
    }
}
