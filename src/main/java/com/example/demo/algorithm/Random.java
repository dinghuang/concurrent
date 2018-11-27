package com.example.demo.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

/**
 * java随机数算法
 * 线性同余算法，是一个线性方程，Xn是一个种子，a、c、m都是参数，m是设置随机数最得大上限，
 * 通过种子生成下一个，下一个作为种子生成下下一个随机数。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/11/27
 */
public class Random {

    private static final AtomicLong seedUniquifier = new AtomicLong(8682522807148012L);

    private static AtomicLong seed = null;

    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;

    public Random() {
        long a;
        for (; ; ) {
            long current = seedUniquifier.get();
            long next = current * 181783497276652981L;
            if (seedUniquifier.compareAndSet(current, next)) {
                a = next;
                break;
            }
        }
        seed = new AtomicLong((a ^ multiplier) & mask);
    }

    public Random(long seeds) {
        seed = new AtomicLong((seeds ^ multiplier) & mask);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Random().getRandoms(0, 100, 100)));
    }

    private int nextInt(int bound) {
        int r = next(31);
        int m = bound - 1;
        if ((bound & m) == 0) {
            // i.e., bound is a power of 2
            r = (int) ((bound * (long) r) >> 31);
        } else {
            for (int u = r; u - (r = u % bound) + m < 0; u = next(31)) ;
        }
        return r;
    }

    protected static int next(int bits) {
        long oldSeed;
        long nextSeed;
        AtomicLong tempSeed = seed;
        do {
            oldSeed = tempSeed.get();
            nextSeed = (oldSeed * multiplier + addend) & mask;
        } while (!tempSeed.compareAndSet(oldSeed, nextSeed));
        return (int) (nextSeed >>> (48 - bits));
    }

    /**
     * 根据min和max随机生成count个不重复的随机数组
     *
     * @param min   min
     * @param max   max
     * @param count count
     * @return int[]
     */
    public int[] getRandoms(int min, int max, int count) {
        Random random = new Random();
        int[] randoms = new int[count];
        List<Integer> listRandom = new ArrayList<>();

        if (count > (max - min + 1)) {
            return new int[0];
        }
        // 将所有的可能出现的数字放进候选list
        for (int i = min; i <= max; i++) {
            listRandom.add(i);
        }
        // 从候选list中取出放入数组，已经被选中的就从这个list中移除
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(listRandom.size());
            randoms[i] = listRandom.get(index);
            listRandom.remove(index);
        }

        return randoms;
    }
}
