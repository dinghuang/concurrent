package com.example.demo.algorithm;

/**
 * 集合
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */
public interface Set<E> {

    /**
     * 向集合中添加元素
     *
     * @param e
     * @return void
     */
    void add(E e);

    /**
     * 删除集合中的某个元素
     *
     * @param e
     * @return void
     */
    void remove(E e);

    /**
     * 查看是否包含某个元素
     *
     * @param e
     * @return boolean
     */
    boolean contains(E e);

    /**
     * 获取集合的大小
     *
     * @param
     * @return int
     */
    int getSize();

    /**
     * 判断集合是否为空
     *
     * @param
     * @return boolean
     */
    boolean isEmpty();
}
