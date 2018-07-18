package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class SystemProductBuilder {

    public SystemProduct prepareMacSystem() {
        SystemProduct systemProduct = new SystemProduct();
        systemProduct.addProduct(new MacSystem());
        return systemProduct;
    }

    public SystemProduct prepareAllSystem() {
        SystemProduct systemProduct = new SystemProduct();
        systemProduct.addProduct(new WindowsSystem());
        systemProduct.addProduct(new MacSystem());
        return systemProduct;
    }
}
