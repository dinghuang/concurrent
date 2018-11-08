package com.example.demo.algorithm;

import java.util.Arrays;
import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/11/5
 */
public interface SortAlgorithm {

    <T extends Comparable<T>> T[] sort(T[] unsorted);

    @SuppressWarnings("unchecked")
    default <T extends Comparable<T>> List<T> sort(List<T> unsorted) {
        return Arrays.asList(sort(unsorted.toArray((T[]) new Comparable[unsorted.size()])));
    }
}
