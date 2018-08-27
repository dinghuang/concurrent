package com.example.demo.lock.locktype;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁如果已经被别的线程获取，调用者就一直循环在那里看是否该自旋锁的保持者已经释放了锁，”自旋”一词就是因此而得名。
 * <p>
 * 自旋锁是一种非阻塞锁，也就是说，如果某线程需要获取自旋锁，但该锁已经被其他线程占用时，
 * 该线程不会被挂起，而是在不断的消耗CPU的时间，不停的试图获取自旋锁。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/8/26
 */
public class SpinLock {

    private AtomicReference<Thread> sign = new AtomicReference<>();

    /**
     * 如果是多核处理器，预计线程等待锁的时间很短，短到比线程两次上下文切换时间要少的情况下，使用自旋锁是划算的。
     * 如果是单核处理器，一般建议不要使用自旋锁。因为，在同一时间只有一个线程是处在运行状态，那如果运行线程发现无
     * 法获取锁，只能等待解锁，但因为自身不挂起，所以那个获取到锁的线程没有办法进入运行状态，只能等到运行线程把操作系统分
     * 给它的时间片用完，才能有机会被调度。这种情况下使用自旋锁的代价很高。
     * <p>
     * 如果加锁的代码经常被调用，但竞争情况很少发生时，应该优先考虑使用自旋锁，自旋锁的开销比较小，互斥量的开销较大。
     */
    public void lock() {
        Thread current = Thread.currentThread();
        //CAS原子操作将sign从期望的null设置为当前线程
        while (!sign.compareAndSet(null, current)) {
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        sign.compareAndSet(current, null);
    }
}
