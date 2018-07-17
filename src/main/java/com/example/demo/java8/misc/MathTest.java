package com.example.demo.java8.misc;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/13
 */
public class MathTest {
    public static void main(String[] args) {
        testMathExact();
//        testUnsignedInt();
    }

    private static void testMathExact() {
        try {
            Integer.parseUnsignedInt("-123", 10);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        long maxUnsignedInt = (1L << 32) - 1;
        System.out.println(maxUnsignedInt);
        String string = String.valueOf(maxUnsignedInt);
        System.out.println(string);
        //虽然是无符号的，但是超过int最大长度的时候，返回-1
        int unsignedInt = Integer.parseUnsignedInt(string, 10);
        System.out.println(unsignedInt);
        String string2 = Integer.toUnsignedString(unsignedInt, 10);
        System.out.println(string2);
        try {
            Integer.parseInt(string, 10);
        }
        catch (NumberFormatException e) {
            System.err.println("could not parse signed int of " + maxUnsignedInt);
        }
    }
}
