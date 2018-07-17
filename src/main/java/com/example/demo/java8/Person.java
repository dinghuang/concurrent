package com.example.demo.java8;

import com.google.common.base.MoreObjects;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/13
 */
public class Person {

    public String firstName;
    public String lastName;

    public Person() {}

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .toString();
    }
}

