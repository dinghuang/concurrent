package com.example.demo.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 通过一个缓存示例说明读书锁的使用方式
 *
 * @author dinghuang123@gmail.com
 * @since 2018/5/27
 */
public class Cache {
    static Map<String, Object> map = new HashMap<>();
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();
    static Boolean update = false;

    //获取一个key对应的value
    public static final Object get(String key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    //设置key对应的value，并返回旧的value
    public static final Object put(String key, Object value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }

    //清空所有的内容
    public static final void ckear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }

    //降级锁，数据不经常变化，多线程并发处理数据，数据变更后，线程感知到变化，进行数据的准备工作，同时处理线程被阻塞
    //知道当前线程完成数据的准备工作
    public void processData() {
        r.lock();
        if (!update) {
            //必须先释放读锁
            r.unlock();
            //锁降级从写锁获取到开始
            w.lock();
            try {
                if (!update) {
                    //准备数据的流程...
                    update = true;
                }
                r.lock();
            } finally {
                w.unlock();
            }
            //锁降级完成，写锁降级为读锁
        }
        try {
            //使用数据的流程...
        } finally {
            r.unlock();
        }
    }


}
