package com.example.demo.designpattern.createfactory;


/**
 * 创建一个抽象类 Prototype 和扩展了 Prototype 类的实体类。下一步是定义类 PrototypeCache，该类把 Prototype
 * 对象存储在一个 Hashtable 中，并在请求的时候返回它们的克隆。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public abstract class Prototype implements Cloneable{

    private String id;

    protected String type;

    abstract void operation();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Object clone(){
        Object clone = null;
        try{
            clone = super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return clone;
    }
}
