package com.example.demo.algorithm.lexorank;

/**
 * JIRA的lexoRank（字符串排序算法）
 * jira还结合了平衡器，比较复杂，这里简单实现
 *
 * @author dinghuang123@gmail.com
 * @since 2018/11/27
 */
public class RankTest {

    public static void main(String[] args) {
        String mid = mid();
        System.out.printf("mid>>%s%n", mid);
        String next = genNext(mid);
        System.out.printf("genNext>>%s%n", next);
        String pre = genPre(mid);
        System.out.printf("genPre>>%s%n", pre);
        String between = between(pre, mid);
        System.out.printf("between>>%s%n", between);

    }

    private static String mid() {
        LexoRank minRank = LexoRank.min();
        LexoRank maxRank = LexoRank.max();
        return minRank.between(maxRank).format();
    }

    private static String genNext(String rank) {
        return LexoRank.parse(rank).genNext().format();
    }

    private static String genPre(String minRank) {
        return LexoRank.parse(minRank).genPrev().format();
    }

    private static String between(String leftRank, String rightRank) {
        LexoRank left = LexoRank.parse(leftRank);
        LexoRank right = LexoRank.parse(rightRank);
        return left.between(right).format();
    }
}
