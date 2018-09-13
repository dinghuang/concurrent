package com.example.demo.designpattern.behavioralmodel;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/9/13
 */
public class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }

    public void sendMessage(String message) {
        ChatRoom.showMessage(this, message);
    }
}
