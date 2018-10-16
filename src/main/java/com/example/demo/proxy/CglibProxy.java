package com.example.demo.proxy;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */
public class CglibProxy implements MethodInterceptor {
    private Object target;

    public Object getProxyInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // call back method
        enhancer.setCallback(this);
        // create proxy instance
        return enhancer.create();
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before target method...");
        Object result = proxy.invokeSuper(target, args);
        System.out.println("after target method...");
        return result;
    }
}