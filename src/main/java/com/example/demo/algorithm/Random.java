package com.example.demo.algorithm;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

/**
 * java随机数算法
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

    public static void main(String[] args) {

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("输入种子数：");
            long a = sc.nextLong();
            if (a == 0) {
                for (; ; ) {
                    long current = seedUniquifier.get();
                    long next = current * 181783497276652981L;
                    if (seedUniquifier.compareAndSet(current, next)) {
                        a = next;
                        break;
                    }
                }
            }
            seed = new AtomicLong((a ^ multiplier) & mask);
            System.out.print("seed" + seed);
            System.out.println("输入范围：");
            int b = sc.nextInt();
            System.out.print("随机数是：");
            for (int d = 0; d < b; d++) {
                if (b == 1 || (d == (b - 1))) {
                    System.out.println(nextInt(b));
                } else {
                    System.out.print(nextInt(b) + ",");
                }
            }
            System.out.println("是否结束：y/n?：");
            String condition = sc.next();
            if ("y".equals(condition)) {
                break;
            }
        }
    }

    private static int nextInt(int bound) {
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
            System.out.print(oldSeed);
            System.out.print(nextSeed);
            System.out.print(tempSeed.compareAndSet(oldSeed, nextSeed));
        } while (!tempSeed.compareAndSet(oldSeed, nextSeed));
        return (int) (nextSeed >>> (48 - bits));
    }
}
