package com.example.demo.serializable;

import java.io.*;

/**
 * 测试代码中使用了IO流，但是并没有显示的关闭他。
 * 这其实是Java 7中的新特性try-with-resources。这其实是Java中的一个语法糖
 * ，背后原理其实是编译器帮我们做了关闭IO流的工作。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/12/24
 */
public class SerializableDemo {
    public static void main(String[] args) {
        //Initializes The Object
        UserSerializable user = new UserSerializable();
        user.setName("dinghuang");
        user.setAge(23);
        System.out.println(user);

        //Write Obj to File
        try (FileOutputStream fos = new FileOutputStream("tempFile");
             ObjectOutputStream oos = new ObjectOutputStream( fos)) {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Read Obj from File
        File file = new File("tempFile");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            UserSerializable newUser = (UserSerializable) ois.readObject();
            System.out.println(newUser);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
