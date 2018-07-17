package com.example.demo.java8.misc;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/16
 */
public class StringTest {
    public static void main(String[] args) {
        //字符串拼接
        testJoin();
        //转换为char后去重再排序转换为String
        testChars();
        //利用正则表达式过滤筛选
        testPatternPredicate();
        //分割字符串后过滤
        testPatternSplit();
    }

    private static void testPatternSplit() {
        String string = Pattern.compile(":")
                .splitAsStream("foobar:foo:bar")
                .filter(s -> s.contains("bar"))
                .sorted()
                .collect(Collectors.joining(":"));
        System.out.println(string);
    }

    private static void testPatternPredicate() {
        long count = Stream.of("dinghuang123@gmail.com", "dinghuang123@hotmail.com")
                .filter(Pattern.compile(".*@gmail\\.com").asPredicate())
                .count();
        System.out.println(count);
    }

    private static void testChars() {
        String a = "foobar:foo:bar";
        String string = "foobar:foo:bar"
                .chars()
                .distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .sorted()
                .collect(Collectors.joining());
        System.out.println(string);
    }

    private static void testJoin() {
        String string = String.join(":", "foobar", "foo", "bar");
        System.out.println(string);
    }
}
