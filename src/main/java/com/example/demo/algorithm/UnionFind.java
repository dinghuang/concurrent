package com.example.demo.algorithm;

/**
 * 并查集
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */
public interface UnionFind {

    /**
     * 获取并查集的大小
     *
     * @return int
     */
    int getSize();

    /**
     * 查询两个元素是否相连
     *
     * @param p p
     * @param q q
     * @return boolean
     */
    boolean isConnected(int p, int q);

    /**
     * 将两个元素并在一起
     *
     * @param p p
     * @param q q
     */
    void unionElements(int p, int q);
}