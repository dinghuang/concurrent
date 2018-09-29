package com.example.demo.algorithm;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */
public interface Stack<E> {

    /**
     * 获取栈的大小
     *
     * @param
     * @return int
     */
    int getSize();

    /**
     * 判断栈是否为空
     *
     * @param
     * @return boolean
     */
    boolean isEmpty();

    /**
     * 向栈中插入一个元素
     *
     * @param e
     * @return void
     */
    void push(E e);

    /**
     * 向栈中移除一个元素
     *
     * @param
     * @return E
     */
    E pop();

    /**
     * 查看栈顶元素
     *
     * @param
     * @return E
     */
    E peek();
}
