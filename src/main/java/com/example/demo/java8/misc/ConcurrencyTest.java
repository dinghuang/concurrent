package com.example.demo.java8.misc;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/13
 */
public class ConcurrencyTest {

    public static void main(String[] args) {
        ConcurrentHashMap<Integer, UUID> concurrentHashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 100; i++) {
            concurrentHashMap.put(i,UUID.randomUUID());
        }
        //此操作并行执行所需的(估计的)元素数量*
        int threshold = 1;
        concurrentHashMap.forEachValue(threshold,System.out::println);
        concurrentHashMap.forEach((id, uuid) -> {
            if (id % 10 == 0) {
                System.out.println(String.format("%s: %s", id, uuid));
            }
        });
        //搜索得出符合的搜索结果
        UUID searchResult = concurrentHashMap.search(threshold, (id, uuid) -> {
            if (String.valueOf(uuid).startsWith(String.valueOf(id))) {
                System.out.println((String.format("%s: %s", id, uuid)));
                return uuid;
            }
            return null;
        });
        System.out.println(searchResult);
    }
}
