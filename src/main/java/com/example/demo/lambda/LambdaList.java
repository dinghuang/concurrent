package com.example.demo.lambda;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream原理深度解析
 *
 * @author dinghuang123@gmail.com
 */
public class LambdaList {

    public static void main(String[] args) {
        List<String> list = new ArrayList(Arrays.asList("bcd", "cde", "def", "abc"));
        List<String> result = list.stream()
                //.parallel()
                .filter(e -> e.length() >= 3)
                .map(e -> e.charAt(0))
                //.peek(System.out :: println)
                //.sorted()
                //.peek(e -> System.out.println("++++" + e))
                .map(e -> String.valueOf(e))
                .collect(Collectors.toList());
        System.out.println("----------------------------");
        System.out.println(result);
    }

}
