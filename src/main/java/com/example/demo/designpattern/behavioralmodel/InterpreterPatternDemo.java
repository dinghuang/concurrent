package com.example.demo.designpattern.behavioralmodel;

/**
 * 解释器模式
 * <p>
 * 创建一个接口 Expression 和实现了 Expression 接口的实体类。定义作为上下文中主要解释器的
 * TerminalExpression 类。其他的类 OrExpression、AndExpression 用于创建组合式表达式。
 * <p>
 * InterpreterPatternDemo，我们的演示类使用 Expression 类创建规则和演示表达式的解析。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/13
 */
public class InterpreterPatternDemo {

    /**
     * 规则：Robert 和 John 是男性
     */
    public static Expression getMaleExpression() {
        Expression robert = new TerminalExpression("Robert");
        Expression john = new TerminalExpression("John");
        return new OrExpression(robert, john);
    }

    /**
     * 规则：Julie 是一个已婚的女性
     */
    public static Expression getMarriedWomanExpression() {
        Expression julie = new TerminalExpression("Julie");
        Expression married = new TerminalExpression("Married");
        return new AndExpression(julie, married);
    }

    public static void main(String[] args) {
        Expression isMale = getMaleExpression();
        Expression isMarriedWoman = getMarriedWomanExpression();

        System.out.println("John is male? " + isMale.interpret("John"));
        System.out.println("Julie is a married women? "
                + isMarriedWoman.interpret("Married Julie"));
    }
}
