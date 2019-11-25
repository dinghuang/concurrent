package com.example.demo.leetcode.heap;

import java.util.*;

/**
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * 示例 2:
 * <p>
 * 输入: nums = [1], k = 1
 * 输出: [1]
 * 说明：
 * <p>
 * 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
 * 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
 *
 * @author dinghuang123@gmail.com
 * @since 2019/11/25
 */
public class TopKFrequent {
    public static List<Integer> topKFrequent(int[] nums, int k) {
        //建立堆，堆中添加一个元素的复杂度是 O(log(k))，要进行 N 次复杂度是 O(N)。
        //最后一步是输出结果，复杂度为 O(klog(k))。
        HashMap<Integer, Integer> count = new HashMap<>(nums.length);
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(count.size(), Comparator.comparingInt(count::get));
        for (int n : count.keySet()) {
            heap.add(n);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        List<Integer> result = new ArrayList<>(heap);
        while (!heap.isEmpty()) {
            result.set(heap.size() - 1, heap.poll());
        }
        return result;

    }

    public static void main(String[] args) {
        int[] a = {1, 1, 1, 1, 2, 2, 3};
        System.out.println(topKFrequent(a, 2));
    }
}
