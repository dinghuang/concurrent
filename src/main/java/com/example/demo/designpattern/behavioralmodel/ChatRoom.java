package com.example.demo.designpattern.behavioralmodel;

import java.util.Date;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/9/13
 */
public class ChatRoom {
    public static void showMessage(User user, String message) {
        System.out.println(new Date().toString()
                + " [" + user.getName() + "] : " + message);
    }
}
