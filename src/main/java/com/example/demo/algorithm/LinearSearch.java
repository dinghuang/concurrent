package com.example.demo.algorithm;

import java.util.Random;
import java.util.stream.Stream;

/**
 * 线性搜索
 * <p>
 * 坏的情况下的性能 O(n)
 * 表现最好的情况下 O(1)
 * 平均情况下的性能 O(n)
 * 最坏情况空间复杂度 O(1) iterative
 *
 * @author dinghuang123@gmail.com
 * @since 2018/11/6
 */
public class LinearSearch implements SearchAlgorithm {
    @Override
    public <T extends Comparable<T>> int find(T[] array, T value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].compareTo(value) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        //just generate data
        Random r = new Random();
        int size = 200;
        int maxElement = 100;
        Integer[] integers = Stream.generate(() -> r.nextInt(maxElement)).limit(size).toArray(Integer[]::new);


        //the element that should be found
        Integer shouldBeFound = integers[r.nextInt(size - 1)];

        LinearSearch search = new LinearSearch();
        int atIndex = search.find(integers, shouldBeFound);

        System.out.println(String.format("Should be found: %d. Found %d at index %d. An array length %d"
                , shouldBeFound, integers[atIndex], atIndex, size));
    }
}
