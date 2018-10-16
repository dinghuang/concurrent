package com.example.demo.proxy;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */
public class People {
    Printer printer = new Printer();

    /**
     * 同步回调
     *
     * @param callback callback
     * @param text     text
     */
    public void goToPrintSyn(CallbackTest callback, String text) {
        printer.print(callback, text);
    }

    /**
     * 异步回调
     *
     * @param callback callback
     * @param text     text
     */
    public void goToPrintASyn(CallbackTest callback, String text) {
        new Thread(() -> printer.print(callback, text)).start();
    }
}
