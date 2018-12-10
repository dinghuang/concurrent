package com.example.demo.algorithm.lru;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/12/10
 */
public class LRUCache<K, V> {

    private int cacheCapcity;
    private HashMap<K, CacheNode> caches;
    private CacheNode first;
    private CacheNode last;
    private final Lock lock = new ReentrantLock();

    public LRUCache(int size) {
        this.cacheCapcity = size;
        caches = new HashMap<>(size);
    }

    public void put(K k, V v) {
        lock.lock();
        try {
            CacheNode node = caches.get(k);
            if (node == null) {
                if (caches.size() >= cacheCapcity) {

                    caches.remove(last.key);
                    removeLast();
                }
                node = new CacheNode();
                node.key = k;
            }
            node.value = v;
            moveToFirst(node);
            caches.put(k, node);
        } finally {
            lock.unlock();
        }

    }

    public int size() {
        lock.lock();
        try {
            return caches.size();
        } finally {
            lock.unlock();
        }
    }

    public Object get(K k) {
        lock.lock();
        try {
            CacheNode node = caches.get(k);
            if (node == null) {
                return null;
            }
            moveToFirst(node);
            return node.value;
        } finally {
            lock.unlock();
        }
    }

    public Object remove(K k) {
        lock.lock();
        try {
            CacheNode node = caches.get(k);
            if (node != null) {
                if (node.pre != null) {
                    node.pre.next = node.next;
                }
                if (node.next != null) {
                    node.next.pre = node.pre;
                }
                if (node == first) {
                    first = node.next;
                }
                if (node == last) {
                    last = node.pre;
                }
            }
            return caches.remove(k);
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        lock.lock();
        try {
            first = null;
            last = null;
            caches.clear();
        } finally {
            lock.unlock();
        }
    }


    private void moveToFirst(CacheNode node) {
        lock.lock();
        try {
            if (first == node) {
                return;
            }
            if (node.next != null) {
                node.next.pre = node.pre;
            }
            if (node.pre != null) {
                node.pre.next = node.next;
            }
            if (node == last) {
                last = last.pre;
            }
            if (first == null || last == null) {
                first = last = node;
                return;
            }

            node.next = first;
            first.pre = node;
            first = node;
            first.pre = null;
        } finally {
            lock.unlock();
        }
    }

    private void removeLast() {
        lock.lock();
        try {
            if (last != null) {
                last = last.pre;
                if (last == null) {
                    first = null;
                } else {
                    last.next = null;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        lock.lock();
        try {
            StringBuilder sb = new StringBuilder();
            CacheNode node = first;
            while (node != null) {
                sb.append(String.format("%s:%s ", node.key, node.value));
                node = node.next;
            }
            return sb.toString();
        } finally {
            lock.unlock();
        }
    }

    class CacheNode {
        CacheNode pre;
        CacheNode next;
        Object key;
        Object value;

        public CacheNode() {

        }
    }

    public static void main(String[] args) {

        LRUCache<Integer, String> lru = new LRUCache<>(3);
        lru.put(1, "a");
        System.out.println(lru.toString());
        lru.put(2, "b");
        System.out.println(lru.toString());
        lru.put(3, "c");
        System.out.println(lru.toString());
        lru.put(4, "d");
        System.out.println(lru.toString());
        lru.put(1, "aa");
        System.out.println(lru.toString());
        lru.put(2, "bb");
        System.out.println(lru.toString());
        lru.put(5, "e");
        System.out.println(lru.toString());
        lru.get(1);
        System.out.println(lru.toString());
        lru.remove(11);
        System.out.println(lru.toString());
        lru.remove(1);
        System.out.println(lru.toString());
        lru.put(1, "aaa");
        System.out.println(lru.toString());
    }

}
