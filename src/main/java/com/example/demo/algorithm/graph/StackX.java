package com.example.demo.algorithm.graph;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/12/4
 */
public class StackX {

    private final int SIZE = 20;
    private int[] stack;
    private int top;

    public StackX() {
        this.stack = new int[SIZE];
        top = -1;
    }

    public void push(int j) {
        stack[++top] = j;
    }

    public int pop() {
        return stack[top--];
    }

    public int peek() {
        return stack[top];
    }

    public boolean isEmpty() {
        return (top == -1);
    }
}
