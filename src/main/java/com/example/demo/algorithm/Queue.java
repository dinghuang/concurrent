package com.example.demo.algorithm;

/**
 * 队列
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */
public interface Queue<E> {

    /**
     * 获取队列的大小
     *
     * @param
     * @return int
     */
    int getSize();

    /**
     * 查看队列是否为空
     *
     * @param
     * @return boolean
     */
    boolean isEmpty();

    /**
     * 将一个元素插入队尾
     *
     * @param e
     * @return void
     */
    void enqueue(E e);

    /**
     * 将队首一个元素移除队列
     *
     * @param
     * @return E
     */
    E dequeue();

    /**
     * 获取队首的一个元素
     *
     * @param
     * @return E
     */
    E getFront();
}
