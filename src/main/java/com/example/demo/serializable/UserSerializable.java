package com.example.demo.serializable;

import java.io.Serializable;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/12/24
 */
public class UserSerializable implements Serializable {

    private String name;
    /**
     * transient 关键字的作用是控制变量的序列化，在变量声明前加上该关键字，可以阻止该变量被序列化到文件中，在被反序列化后，
     * transient 变量的值被设为初始值，如 int 型的是 0，对象型的是 null
     */
    transient private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserSerializable{" + "name='" + name + '\'' + ", age=" + age + '}';
    }
}
