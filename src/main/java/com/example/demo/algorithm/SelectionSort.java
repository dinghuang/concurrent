package com.example.demo.algorithm;

import static com.example.demo.algorithm.SortUtils.*;

/**
 * 选择排序
 * <p>
 * 坏的情况下的性能 O(n^2)
 * 表现最好的情况下 O(n^2)
 * 平均情况下的性能 O(n^2)
 *
 * @author dinghuang123@gmail.com
 * @since 2018/11/5
 */
public class SelectionSort implements SortAlgorithm {

    /**
     * This method implements the Generic Selection Sort
     *
     * @param arr The array to be sorted
     *            Sorts the array in increasing order
     **/
    @Override
    public <T extends Comparable<T>> T[] sort(T[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            // Initial index of min
            int min = i;

            for (int j = i + 1; j < n; j++) {
                if (less(arr[j], arr[min])) {
                    min = j;
                }
            }

            // Swapping if index of min is changed
            if (min != i) {
                swap(arr, i, min);
            }
        }

        return arr;
    }

    public static void main(String[] args) {

        Integer[] arr = {4, 23, 6, 78, 1, 54, 231, 9, 12};

        SelectionSort selectionSort = new SelectionSort();

        Integer[] sorted = selectionSort.sort(arr);

        // Output => 1	  4	 6	9	12	23	54	78	231
        print(sorted);

        // String Input
        String[] strings = {"c", "a", "e", "b", "d"};
        String[] sortedStrings = selectionSort.sort(strings);

        //Output => a	b	 c  d	e
        print(sortedStrings);
    }
}
