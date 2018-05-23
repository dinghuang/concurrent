package com.example.demo.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/5/22
 */
public class AlphabeticallySort {
    public static void main(String[] args) {
        String min = "0|aaaa";
        String max = "0|aaab";
        String middleNum = generateMiddleNum(max, min, 4);
        List<String> sort = new ArrayList<>();
        sort.add(max);
        sort.add(min);
        sort.add(middleNum);
        System.out.println(middleNum);
        Collections.sort(sort, String.CASE_INSENSITIVE_ORDER);
        System.out.println(sort.toString());
    }

    private static String generateMiddleNum(String max, String min, Integer size) {
        String middleNum = min;
        char a = min.charAt(min.length() - 1);
        if (min.length() + 1 < size) {
            middleNum = middleNum + "a";
        } else {
            int ai = (int) a;
            ai++;
            char newA = (char) ai;
            middleNum = middleNum + newA;
        }
        if (stringToAscii(max) > stringToAscii(middleNum)) {
            return middleNum;
        } else if (stringToAscii(max) == stringToAscii(middleNum)) {
            return max + "a";
        } else {
            char c = max.charAt(max.length() - 1);
            c--;
            return min + (char) c;
        }
    }


    private static int stringToAscii(String str) {
        char[] chars = str.toCharArray();
        int num = 0;
        for (char aChar : chars) {
            num += (int) aChar;
        }
        return num;
    }

}
