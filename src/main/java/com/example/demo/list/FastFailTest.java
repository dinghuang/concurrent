package com.example.demo.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List 的FastFail
 * @author dinghuang123@gmail.com
 * @since 2018/9/22
 */
public class FastFailTest {


    /**
     * The largest possible table capacity.  This value must be
     * exactly 1<<30 to stay within Java array allocation and indexing
     * bounds for power of two table sizes, and is further required
     * because the top two bits of 32bit hash fields are used for
     * control purposes.
     */
    private static final int MAXIMUM_CAPACITY = 1 << 30;


    private static List<String> list = new ArrayList<String>();

    //private static List<String> list = new CopyOnWriteArrayList<String>();


    public static void main(String[] args) {

        // 同时启动两个线程对list进行操作！
//        new ThreadOne().start();
//        new ThreadTwo().start();>> 1
        System.out.println(199>>1);
        System.out.println(199>>>1);
        System.out.println(1 << 30);
        System.out.println(tableSizeFor(1025));
        System.out.println(arrToStr(new String[]{"a","b","c"}));
    }

    public static String arrToStr(String[] params) {
        String a = Arrays.stream(params).collect(Collectors.joining(","));
        System.out.println(a);
        if (params == null) {
            return null;
        }
        if (params.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (String ele : params) {
                sb.append(ele).append(",");
            }
            return sb.toString();
        }
        String param = null;
        if (params.length == 1) {
            param = params[0];
        }
        return param;
    }

    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    private static void printAll() {
        System.out.println("");

        String value = null;
        Iterator iter = list.iterator();
        while(iter.hasNext()) {
            value = (String)iter.next();
            System.out.print(value+", ");
        }
    }

    /**
     * 向list中依次添加0,1,2,3,4,5，每添加一个数之后，就通过printAll()遍历整个list
     */
    private static class ThreadOne extends Thread {
        public void run() {
            int i = 0;
            while (i<6) {
                list.add(String.valueOf(i));
                printAll();
                i++;
            }
        }
    }

    /**
     * 向list中依次添加10,11,12,13,14,15，每添加一个数之后，就通过printAll()遍历整个list
     */
    private static class ThreadTwo extends Thread {
        public void run() {
            int i = 10;
            while (i<16) {
                list.add(String.valueOf(i));
                printAll();
                i++;
            }
        }
    }
}
