package com.example.demo.serializable;

import java.io.Serializable;

/**
 * 单例模式可序列化（序列化过程不破坏单例）
 *
 * @author dinghuang123@gmail.com
 * @since 2018/12/24
 */
public class UserSerializable implements Serializable {

    /**
     * 虚拟机是否允许反序列化，不仅取决于类路径和功能代码是否一致，一个非常重要的一点是两个类的序列化 ID 是否一致
     */
    private static final long serialVersionUID = -179612182787370459L;

    private volatile static UserSerializable userSerializable;

    private UserSerializable() {
    }

    public static UserSerializable getUserSerializable() {
        if (userSerializable == null) {
            synchronized (UserSerializable.class) {
                if (userSerializable == null) {
                    userSerializable = new UserSerializable();
                }
            }
        }
        return userSerializable;
    }

    /**
     * hasReadResolveMethod:如果实现了serializable 或者 externalizable接口的类中包含readResolve则返回true
     * invokeReadResolve:通过反射的方式调用要被反序列化的类的readResolve方法。
     *
     * @return Object
     */
    protected Object readResolve() {
        return userSerializable;
    }

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
