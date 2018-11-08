package com.example.demo.algorithm;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/11/6
 */
public interface SearchAlgorithm {

    <T extends Comparable<T>> int find (T array[],T key);
}
