package com.example.demo.algorithm;

/**
 * 基于链表实现映射
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */
public class LinkedListMap<K, V> implements Map<K, V> {


    /**
     * 虚拟头节点
     */
    private Node dummyHead;
    /**
     * 链表大小
     */
    private int size;

    public LinkedListMap() {
        dummyHead = new Node(null, null, null);
        size = 0;
    }


    @Override
    public void add(K key, V value) {
        Node node = getNode(key);
        if (node != null) {
            throw new IllegalArgumentException("Add failed. key is exists!");
        }
        dummyHead.next = new Node(key, value, dummyHead.next);
        size++;
    }

    @Override
    public V remove(K key) {
        Node node = getNode(key);
        if (node == null) {
            throw new IllegalArgumentException("Remove failed. key is not exists!");
        }
        Node prev = dummyHead;
        while (prev.next != null) {
            if (prev.next.key.equals(key)) {
                break;
            }
            prev = prev.next;
        }

        if (prev.next != null) {
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size--;
            return delNode.value;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node != null ? node.value : null;
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(key);
        if (node == null) {
            throw new IllegalArgumentException("Set failed. key is not exists!");
        }
        node.value = value;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 根据key获取Node
     *
     * @param key key
     * @return Node
     */
    private Node getNode(K key) {
        Node cur = dummyHead.next;
        while (cur != null) {
            if (cur.key.equals(key)) {
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }

    private class Node {

        private K key;
        private V value;
        private Node next;

        Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key) {
            this.key = key;
        }

        public Node() {
            this(null, null, null);
        }

        @Override
        public String toString() {
            return key.toString() + " : " + value.toString();
        }
    }
}
