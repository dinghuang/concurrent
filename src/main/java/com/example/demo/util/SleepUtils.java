package com.example.demo.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by dinghuang on 2018/2/24.
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}
