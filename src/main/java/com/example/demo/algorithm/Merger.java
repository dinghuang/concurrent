package com.example.demo.algorithm;

/**
 * 融合器
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */

public interface Merger<E> {
    /**
     * 融合两个元素
     *
     * @param a
     * @param b
     * @return E
     */
    E merge(E a, E b);
}
