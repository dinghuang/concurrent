package com.example.demo.designpattern.behavioralmodel;

/**
 * 中介者模式
 * <p>
 * 通过聊天室实例来演示中介者模式。实例中，多个用户可以向聊天室发送消息，聊天室向所有的用户显示消息。
 * 我们将创建两个类 ChatRoom 和 User。User 对象使用 ChatRoom 方法来分享他们的消息。
 * <p>
 * MediatorPatternDemo，我们的演示类使用 User 对象来显示他们之间的通信。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/13
 */
public class MediatorPatternDemo {
    public static void main(String[] args) {
        User robert = new User("Robert");
        User john = new User("John");

        robert.sendMessage("Hi! John!");
        john.sendMessage("Hello! Robert!");
    }
}
