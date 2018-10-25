package com.example.demo.algorithm;

import java.util.Random;

/**
 * 微信红包算法
 *
 * @author dinghuang123@gmail.com
 * @since 2018/10/25
 */
public class WeChatMoney {

    public static double getRandomMoney(LeftMoneyPackage leftMoneyPackage) {
        if (leftMoneyPackage.remainSize == 1) {
            leftMoneyPackage.remainSize--;
            return Math.floor(leftMoneyPackage.remainMoney * 100) / 100;
        }
        Random r = new Random();
        double min = 0.01;
        double max = leftMoneyPackage.remainMoney / leftMoneyPackage.remainSize * 2;
        double money = r.nextDouble() * max;
        money = money <= min ? 0.01 : money;
        money = Math.floor(money * 100) / 100;
        leftMoneyPackage.remainSize--;
        leftMoneyPackage.remainMoney -= money;
        return money;
    }

    public static void main(String[] args) {
        LeftMoneyPackage leftMoneyPackage = new LeftMoneyPackage();
        leftMoneyPackage.setRemainMoney(1000);
        leftMoneyPackage.setRemainSize(10);
        Double count = 0D;
        for (int i = 0; i < 10; i++) {
            Double a = getRandomMoney(leftMoneyPackage);
            count += a;
            System.out.println(a);
        }
        System.out.println(count);

    }

    public static class LeftMoneyPackage {
        private Integer remainSize;
        private double remainMoney;

        public Integer getRemainSize() {
            return remainSize;
        }

        public void setRemainSize(Integer remainSize) {
            this.remainSize = remainSize;
        }

        public double getRemainMoney() {
            return remainMoney;
        }

        public void setRemainMoney(double remainMoney) {
            this.remainMoney = remainMoney;
        }
    }
}
