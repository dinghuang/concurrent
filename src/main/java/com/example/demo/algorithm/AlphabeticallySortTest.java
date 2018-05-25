package com.example.demo.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/5/23
 */
public class AlphabeticallySortTest {

    public static void main(String[] args) {
        String[] sort = {"0|aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "0|aaaaaaaaaaaaaaaaaaaaaaaaqwqwesadasdasdawqeqeqwewqewqeqzx",
                "0|aaaaaaaaaaaaaaaaaaaaaaaaadwqewqeffffffffffffffffffffffffffffffffffffffffffff",
                "0|aa",
                "0|aaaaaaaaaaaaaaaaaaaaaaaawqwe213sd3333333333333333333333333333333333",
                "0|aaaaaaaaaaaaaaaaaaaaaaaaasddddddddddddddddddddawqeqwewqeqwe",
                "0|azzzzzzzzzzzzzzzzzzzzzzza",
                "0|zzzzzz", "0|000000"};
        List<String> sorta = Arrays.asList(sort);
        Collections.sort(sorta, String.CASE_INSENSITIVE_ORDER);
        sorta.forEach(s -> System.out.println(s));
    }

}
