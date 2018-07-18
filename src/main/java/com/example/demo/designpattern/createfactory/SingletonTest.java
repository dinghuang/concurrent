package com.example.demo.designpattern.createfactory;



import java.util.stream.IntStream;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class SingletonTest {
    public static void main(String[] args) {
        //不合法的构造函数
        //编译时错误：构造函数 User() 是私有的
//        User user = new User();
        Runnable runnable = () -> {
            User.USER.sendMessage();
        };
        IntStream.range(0, 100)
                .forEach(i -> {
                    Thread thread = new Thread(runnable);
                    thread.start();
                });

    }
}
