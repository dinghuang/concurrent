package com.example.demo.algorithm;


/**
 * 利用链表实现栈
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */
public class LinkedListStack<E> implements Stack<E> {

    private LinkedList<E> list;

    public LinkedListStack() {
        this.list = new LinkedList<>();
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
    public void push(E e) {
        list.addFirst(e);
    }

    @Override
    public E pop() {
        return list.removeFirst();
    }

    @Override
    public E peek() {
        return list.getFirst();
    }

    @Override
    public String toString() {
        return String.format("Stack: size = %d %n", list.getSize()) +
                "top " +
                list;
    }
}

