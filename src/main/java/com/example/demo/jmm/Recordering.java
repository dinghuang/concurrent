package com.example.demo.jmm;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/8/31
 */
final class Recordering {
    private int  a = 0;
    private long b = 0;

    void set() {
        a =  1;
        b = -1;
    }

    boolean check() {
        return (
                (b == -1 && a == 1));
    }
}
