package com.example.demo.algorithm;

/**
 * 利用链表实现集合
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */
public class LinkedListSet<E> implements Set<E> {

    private LinkedList linkedList;

    public LinkedListSet() {
        linkedList = new LinkedList();
    }

    @Override
    public void add(E e) {
        if (!linkedList.contains(e)) {
            linkedList.addFirst(e);
        }
    }

    @Override
    public void remove(E e) {
        linkedList.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return linkedList.contains(e);
    }

    @Override
    public int getSize() {
        return linkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }
}
