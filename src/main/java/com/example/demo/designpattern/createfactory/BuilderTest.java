package com.example.demo.designpattern.createfactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class BuilderTest {
    public static void main(String[] args) {
        SystemProductBuilder systemProductBuilder = new SystemProductBuilder();
        SystemProduct windows = systemProductBuilder.prepareMacSystem();
        System.out.println("windows product");
        windows.showProducts();
        System.out.println("Total Cost: " + windows.getCost());

        SystemProduct allSystem = systemProductBuilder.prepareAllSystem();
        System.out.println("allSystem");
        allSystem.showProducts();
        System.out.println("Total Cost: " + allSystem.getCost());
    }
}
