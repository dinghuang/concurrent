package com.example.demo.concurrent;

import com.example.demo.dto.Instance;

/**
 * Created by dinghuang on 2018/2/24.
 */
public class SafeDoubleCheckedLocking {

    /**
     * 基于volatile的双重检查锁定来实现延迟初始化的方案
     */
    private volatile static Instance instance;

    public static Instance getInstance() {
        if (instance == null) {
            synchronized (SafeDoubleCheckedLocking.class) {
                if (instance == null) {
                    instance = new Instance();//instance为volatile，现在没问题了
                }
            }
        }
        return instance;
    }

    /**
     * 基于类初始化的线程安全的延迟初始化方案（Initialization On Demand Holder idiom）
     */
    private static class InstanceHolder {
        public static Instance instance = new Instance();
    }

    public static Instance getInstanceOnDemandHolder() {
        return InstanceHolder.instance;//这里将导致InstanceHolder类被初始化
    }

}
