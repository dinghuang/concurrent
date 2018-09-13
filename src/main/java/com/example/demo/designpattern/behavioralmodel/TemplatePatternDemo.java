package com.example.demo.designpattern.behavioralmodel;

/**
 * 模板模式
 * <p>
 * 创建一个定义操作的 Game 抽象类，其中，模板方法设置为 final，这样它就不会被重写。Cricket 和 Football 是扩展了 Game 的实体类，它们重写了抽象类的方法。
 * <p>
 * TemplatePatternDemo，我们的演示类使用 Game 来演示模板模式的用法。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/13
 */
public class TemplatePatternDemo {

    public static void main(String[] args) {

        Game game = new Cricket();
        game.play();
        System.out.println();
        game = new Football();
        game.play();
    }
}
