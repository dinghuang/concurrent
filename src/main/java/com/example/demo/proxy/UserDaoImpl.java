package com.example.demo.proxy;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */

public class UserDaoImpl implements UserDao {
    @Override
    public boolean login(String username, String password) {
        String user = "("+username+","+password+")";
        System.out.println(this.getClass().getName()+"-> processing login:"+user);
        return true;
    }
}