package com.example.demo.algorithm;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */
public interface Map<K, V> {

    /**
     * 添加元素
     *
     * @param key
     * @param value
     */
    void add(K key, V value);

    /**
     * 删除元素
     *
     * @param key
     * @return V
     */
    V remove(K key);

    /**
     * 查看是否包含某个key
     *
     * @param key
     * @return boolean
     */
    boolean contains(K key);

    /**
     * 获取某个key对应的value
     *
     * @param key
     * @return V
     */
    V get(K key);

    /**
     * 更新某个key对应的value
     *
     * @param key
     * @param value
     */
    void set(K key, V value);

    /**
     * 获取大小
     *
     * @param
     * @return int
     */
    int getSize();

    /**
     * 查看是否为空
     *
     * @param
     * @return boolean
     */
    boolean isEmpty();
}
