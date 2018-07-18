package com.example.demo.designpattern.createfactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class SystemProduct {

    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public float getCost() {
        float cost = 0.0f;
        for (Product product : products) {
            cost += product.price();
        }
        return cost;
    }

    public void showProducts() {
        for (Product product : products) {
            System.out.print("Product : " + product.name());
            System.out.print(",Production : " + product.production().production());
            System.out.println(", Price : " + product.price());
        }
    }
}
