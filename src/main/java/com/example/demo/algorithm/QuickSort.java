package com.example.demo.algorithm;

import java.util.Arrays;

/**
 * 快速排序
 *
 * @author dinghuang123@gmail.com
 * @since 2018/10/26
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] a = {1, 2, 4, 11, 7, 4, 5, 3, 9, 1};
        System.out.println(Arrays.toString(a));
        quickSort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(int[] arr) {
        qsort(arr, 0, arr.length - 1);
    }

    private static void qsort(int[] arr, int low, int high) {
        if (low < high) {
            //将数组分为两部分
            int pivot = partition(arr, low, high);
            //递归排序左子数组
            qsort(arr, low, pivot - 1);
            //递归排序右子数组
            qsort(arr, pivot + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        //枢轴记录
        System.out.println("中枢" + low);
        int pivot = arr[low];
        while (low < high) {
            while (low < high && arr[high] >= pivot) {
                --high;
            }
            //交换比枢轴小的记录到左端
            arr[low] = arr[high];
            while (low < high && arr[low] <= pivot) {
                ++low;
            }
            //交换比枢轴小的记录到右端
            arr[high] = arr[low];
        }
        //扫描完成，枢轴到位
        arr[low] = pivot;
        System.out.println(Arrays.toString(arr));
        System.out.println("中枢" + low);
        //返回的是枢轴的位置
        return low;
    }
}

