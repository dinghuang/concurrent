package com.example.demo.algorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;

/**
 * 二分查找
 * <p>
 * 坏的情况下的性能 O(log n)
 * 表现最好的情况下 O(1)
 * 平均情况下的性能 O(log n)
 * 最坏情况空间复杂度 O(1)
 *
 * @author dinghuang123@gmail.com
 * @since 2018/11/6
 */
public class BinarySearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        return search(array, key, 0, array.length);
    }

    /**
     * This method implements the Generic Binary Search
     *
     * @param array The array to make the binary search
     * @param key   The number you are looking for
     * @param left  The lower bound
     * @param right The  upper bound
     * @return the location of the key
     **/
    private <T extends Comparable<T>> int search(T[] array, T key, int left, int right) {
        int low = left;
        int high = right - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            if (key.compareTo(array[mid]) < 0) {
                low = mid + 1;
            } else if (key.compareTo(array[mid]) > 0) {
                high = mid - 1;
            } else {
                // key found
                return mid;
            }
        }
        // key not found.
        return -(low + 1);
    }

    public static void main(String[] args) {
        Integer[] integers = {1,2,3,5,56,6,4};

        // The element that should be found
        Integer shouldBeFound = 4;

        BinarySearch search = new BinarySearch();
        int atIndex = search.find(integers, shouldBeFound);
        System.out.println(format("Should be found: %d. Found %d at index %d", shouldBeFound, integers[atIndex], atIndex));
        //这个jdk官方的bug？？？
        int toCheck = Arrays.binarySearch(integers, shouldBeFound);
        System.out.println(format("Found by system method at an index: %d. Is equal: %b", toCheck, toCheck == atIndex));
    }
}
