package com.example.demo.designpattern.behavioralmodel;

/**
 * 状态模式
 * <p>
 * 创建一个 State 接口和实现了 State 接口的实体状态类。Context 是一个带有某个状态的类。
 * <p>
 * StatePatternDemo，我们的演示类使用 Context 和状态对象来演示 Context 在状态改变时的行为变化。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/13
 */
public class StatePatternDemo {

    public static void main(String[] args) {
        Context context = new Context();

        StartState startState = new StartState();
        startState.doAction(context);

        System.out.println(context.getState().toString());

        StopState stopState = new StopState();
        stopState.doAction(context);

        System.out.println(context.getState().toString());
    }
}
