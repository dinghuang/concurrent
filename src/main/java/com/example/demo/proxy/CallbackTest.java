package com.example.demo.proxy;

import org.assertj.core.internal.cglib.proxy.Callback;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */
public interface CallbackTest extends Callback {
    void printFinished(String msg);
}
