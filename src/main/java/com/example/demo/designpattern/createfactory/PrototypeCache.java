package com.example.demo.designpattern.createfactory;


import java.util.Hashtable;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class PrototypeCache {

    private static Hashtable<String,Prototype> propertyMap =
            new Hashtable<>();

    public static Prototype getProperType(String id){
        Prototype cachePrototype = propertyMap.get(id);
        return (Prototype) cachePrototype.clone();
    }

    public static void loadCache() {
        PrototypeOne prototypeOne = new PrototypeOne();
        prototypeOne.setId("1");
        propertyMap.put(prototypeOne.getId(),prototypeOne);
        PrototypeTwo prototypeTwo = new PrototypeTwo();
        prototypeTwo.setId("2");
        propertyMap.put(prototypeTwo.getId(),prototypeTwo);
    }
}
