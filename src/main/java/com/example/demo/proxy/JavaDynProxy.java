package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */
public class JavaDynProxy implements InvocationHandler {
    private Object target;

    public Object getProxyInstance(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object result = null;
        System.out.println("before target method...");
        result = method.invoke(target, args);
        System.out.println("after target method...");
        return result;
    }
}
