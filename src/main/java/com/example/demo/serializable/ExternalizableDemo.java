package com.example.demo.serializable;

import java.io.*;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/12/24
 */
public class ExternalizableDemo {
    public static void main(String[] args) {
        //Write Obj to file
        UserExternalizable user = new UserExternalizable();
        user.setName("dinghuang");
        user.setAge(23);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"))) {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Read Obj from file
        File file = new File("tempFile");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            UserExternalizable newInstance = (UserExternalizable) ois.readObject();
            //output
            System.out.println(newInstance);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
