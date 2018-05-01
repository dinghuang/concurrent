package com.example.demo.test;

/**
 * 通过在Synchronized.class同级目录下执行javap -v Synchronized.class查看生成的class文件来分析
 * synchronized关键字的实现细节
 *
 * @author dinghuang123@gmail.com
 */
public class Synchronized {
    public static void main(String[] args){
        //对synchronized Class对象进行加锁
        synchronized (Synchronized.class){
        }
        //静态同步方法，对synchronized Class对象进行加锁
        m();
    }

    public static synchronized void m(){
    }
}
