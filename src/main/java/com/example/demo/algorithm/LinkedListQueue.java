package com.example.demo.algorithm;

/**
 * 利用链表实现队列
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */
public class LinkedListQueue<E> implements Queue<E> {

    private LinkedList<E> list;

    public LinkedListQueue() {
        list = new LinkedList<>();
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        list.addLast(e);
    }

    @Override
    public E dequeue() {
        return list.removeFirst();
    }

    @Override
    public E getFront() {
        return list.getFirst();
    }

    @Override
    public String toString() {
        return String.format("Queue: size = %d %n", list.getSize()) +
                "front [" +
                list +
                "] tail";
    }
}