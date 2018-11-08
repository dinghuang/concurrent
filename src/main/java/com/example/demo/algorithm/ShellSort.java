package com.example.demo.algorithm;

import static com.example.demo.algorithm.SortUtils.*;

/**
 * 希尔排序
 * <p>
 * 坏的情况下的性能 O(nlog2 2n)
 * 表现最好的情况下 O(n log n)
 * 平均情况下的性能取决于间隙序列
 *
 * @author dinghuang123@gmail.com
 * @since 2018/11/6
 */
public class ShellSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        int length = array.length;
        int h = 1;

        while (h < length / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && less(array[j], array[j - h]); j -= h) {
                    swap(array, j, j - h);
                }
            }

            h /= 3;
        }

        return array;
    }

    public static void main(String[] args) {
        Integer[] toSort = {4, 23, 6, 78, 1, 54, 231, 9, 12};

        ShellSort sort = new ShellSort();
        Integer[] sorted = sort.sort(toSort);

        print(sorted);

    }
}
