package com.example.demo.java8.lambda;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/6/29
 */
public class InterfaceTest {
    interface Formule {
        double calculate(int a);

        default double sqrt(int a) {
            return Math.sqrt(positive(a));
        }

        static int positive(int a) {
            return a > 0 ? a : 0;
        }

    }

    private static void test1() {
        Formule formule = new Formule() {
            @Override
            public double calculate(int a) {
                return 0;
            }
        };
        formule.calculate(100);
        formule.sqrt(-23);
        Formule.positive(-4);

    }

    public static void main(String[] args) {
        test1();
    }
}
