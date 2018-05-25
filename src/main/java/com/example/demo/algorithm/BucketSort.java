package com.example.demo.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 桶排序
 * 1设置一个定量的数组当作空桶子。
 * 2寻访序列，并且把项目一个一个放到对应的桶子去。
 * 3对每个不是空的桶子进行排序。
 * 4从不是空的桶子里把项目再放回原来的序列中。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/5/22
 */
public class BucketSort {

    private static final int STEP = 5;

    public static void main(String[] args) {
        int[] arr = {1, 2, 5, 3, 4, 12, 4, 235, 523, 12, 4, 34, 5, 57, 87, 89, 4, 768, 98, 798, 123, 213, 3, 5546, 567, 8, 76};
        bucketSort(arr);
        for (int anArr : arr) {
            System.out.print(anArr + ",");
        }
    }

    private static int indexFor(int a, int min) {
        return (a - min) / STEP;
    }

    private static void bucketSort(int[] arr) {
        int max = arr[0];
        int min = arr[0];
        for (int a : arr) {
            if (max < a) {
                max = a;
            }
            if (min > a) {
                min = a;
            }
        }
        //该值也可根据实际情况选择
        int bucketNum = max / STEP - min / STEP + 1;
        List buckList = new ArrayList<List<Integer>>();
        //创建一个空桶
        for (int i = 1; i <= bucketNum; i++) {
            buckList.add(new ArrayList<Integer>());
        }
        //将值放到桶里面
        for (int i = 0; i < arr.length; i++) {
            int index = indexFor(arr[i], min);
            ((ArrayList<Integer>) buckList.get(index)).add(arr[i]);
        }
        ArrayList<Integer> bucket;
        int index = 0;
        for (int i = 0; i < bucketNum; i++) {
            bucket = (ArrayList<Integer>) buckList.get(i);
            insertSort(bucket);
            for (int k : bucket) {
                arr[index++] = k;
            }
        }

    }

    /**
     * 把桶内元素插入排序
     *
     * @param bucket bucket
     */
    private static void insertSort(List<Integer> bucket) {
        for (int i = 1; i < bucket.size(); i++) {
            int temp = bucket.get(i);
            int j = i - 1;
            for (; j >= 0 && bucket.get(j) > temp; j--) {
                bucket.set(j + 1, bucket.get(j));
            }
            bucket.set(j + 1, temp);
        }
    }
}
