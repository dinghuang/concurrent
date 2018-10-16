package com.example.demo.proxy;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */

public class HelloImpl implements Hello {
    @Override
    public String sayHello(String name) {
        String s = "Hello, "+name;
        System.out.println(this.getClass().getName()+"->"+s);
        return s;
    }
}