package com.example.demo.designpattern.behavioralmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/9/13
 */
public class Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
