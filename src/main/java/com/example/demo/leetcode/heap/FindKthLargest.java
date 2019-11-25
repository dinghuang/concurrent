package com.example.demo.leetcode.heap;

/**
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 * 示例 2:
 * <p>
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 * 说明:
 * <p>
 * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
 * <p>
 *
 * @author dinghuang123@gmail.com
 * @since 2019/11/20
 */
public class FindKthLargest {

    public static int findKthLargest(int[] nums, int k) {
        int[] heap = new int[k];
        for (int i = 0; i < k; i++) {
            heap[i] = nums[i];
        }
        buildHeap(heap);
        for (int i = k; i < nums.length; i++) {
            int number = nums[i];
            if (number > heap[0]) {
                heap[0] = number;
                buildHeap(heap);
            }
        }

        return heap[0];
    }

    private static void buildHeap(int[] heap) {
        for (int i = heap.length / 2; i >= 0; i--) {
            heap(i, heap.length, heap);
        }
    }

    private static void heap(int i, int n, int[] heap) {
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int min = i;

        if (l < n && heap[l] < heap[min]) {
            min = l;
        }

        if (r < n && heap[r] < heap[min]) {
            min = r;
        }

        if (min != i) {
            swap(heap, min, i);
            heap(min, n, heap);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public static void main(String[] args) {
        int[] nums = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        System.out.println(findKthLargest(nums, 4));

    }
}

