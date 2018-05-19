package com.example.demo.lambda;

import com.example.demo.dto.Instance;

/**
 * 函数式编程
 *
 * @author dinghuang123@gmail.com
 */
public class Lambda {

    public static void main(String[] args) {
        test((str) -> {
            Instance instance = new Instance();
            instance.setName(str.toString());
            System.out.println(instance.getName());
        }, "Hello World!");
    }

    static void test(Action action, String str) {
        action.execute(str);
    }

    @FunctionalInterface
    interface Action<T> {
        void execute(T t);
    }
}
