package com.example.demo.java8.misc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/13
 */
public class MapTest {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            //如果键值未重复添加，重复不添加
            map.putIfAbsent(i, "vakl" + i);
        }
        map.forEach((id, val) -> System.out.println(val));
        //如果存在计算
        map.computeIfPresent(3, (num, val) -> val + num);
        System.out.println(map.get(3));
        map.computeIfPresent(9, (num, val) -> null);
        // false
        System.out.println(map.containsKey(9));
        map.computeIfAbsent(23, num -> "val" + num);
        // true
        System.out.println(map.containsKey(23));
        //不能改变值，因为存在相同键所对应的值
        map.putIfAbsent(3, "bam");
        // val33
        System.out.println(map.get(3));
        //如果不存在给默认值
        System.out.println(map.getOrDefault(42, "not found"));      // not found
        map.remove(3, "val3");
        // val33
        System.out.println(map.get(3));
        map.remove(3, "val33");
        // null
        System.out.println(map.get(3));
        map.merge(9, "val9", String::concat);
        // val9
        System.out.println(map.get(9));
        //存在返回，不存在合并一个新的
        map.merge(9, "concat", String::concat);
        // val9concat
        System.out.println(map.get(9));
    }
}
