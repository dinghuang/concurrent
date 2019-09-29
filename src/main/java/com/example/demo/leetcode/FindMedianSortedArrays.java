package com.example.demo.leetcode;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/9/29
 */
public class FindMedianSortedArrays {

    /**
     * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
     * <p>
     * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
     * <p>
     * 你可以假设 nums1 和 nums2 不会同时为空。
     *
     * @param nums1 [1, 3] /[1, 2]
     * @param nums2 [2] / [3, 4]
     * @return 2.0 /  (2 + 3)/2 = 2.5
     */
    private static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;
        return (findKth(nums1, 0, nums2, 0, left) + findKth(nums1, 0, nums2, 0, right)) / 2.0;
    }

    /**
     * i: nums1的起始位置 j: nums2的起始位置
     *
     * @param nums1 nums1
     * @param i     nums1的起始位置
     * @param nums2 nums2
     * @param j     nums2的起始位置
     * @param k
     * @return int
     */
    private static int findKth(int[] nums1, int i, int[] nums2, int j, int k) {
        //nums1为空数组
        if (i >= nums1.length) {
            return nums2[j + k - 1];
        }
        //nums2为空数组
        if (j >= nums2.length) {
            return nums1[i + k - 1];
        }
        if (k == 1) {
            return Math.min(nums1[i], nums2[j]);
        }
        int midVal1 = (i + k / 2 - 1 < nums1.length) ? nums1[i + k / 2 - 1] : Integer.MAX_VALUE;
        int midVal2 = (j + k / 2 - 1 < nums2.length) ? nums2[j + k / 2 - 1] : Integer.MAX_VALUE;
        if (midVal1 < midVal2) {
            return findKth(nums1, i + k / 2, nums2, j, k - k / 2);
        } else {
            return findKth(nums1, i, nums2, j + k / 2, k - k / 2);
        }
    }

    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, new int[]{3, 4}));
    }
}
