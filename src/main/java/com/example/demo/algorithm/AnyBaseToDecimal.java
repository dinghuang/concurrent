package com.example.demo.algorithm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/11/7
 */
public class AnyBaseToDecimal {

    public static void main(String[] args) throws Exception {
        System.out.print("Enter number: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String inp = br.readLine();
        System.out.print("Enter base: ");
        int base = Integer.parseInt(br.readLine());

        System.out.println("Input in base " + base + " is: " + inp);
        System.out.println("Decimal value of " + inp + " is: " + convertToDecimal(inp, base));

        br.close();
    }

    /**
     * This method produces a decimal value of any given input number of any base
     *
     * @param inputNum String of which we need the decimal value and base in integer format
     * @return string format of the decimal value
     */

    public static String convertToDecimal(String inputNum, int base) {
        int len = inputNum.length();
        int num = 0;
        int pow = 1;

        for (int i = len - 1; i >= 0; i--) {
            if (valOfChar(inputNum.charAt(i)) >= base) {
                return "Invalid Number";
            }
            num += valOfChar(inputNum.charAt(i)) * pow;
            pow *= base;
        }
        return String.valueOf(num);
    }

    /**
     * This method produces integer value of the input character and returns it
     *
     * @param c Char of which we need the integer value of
     * @return integer value of input char
     */

    public static int valOfChar(char c) {
        if (c >= '0' && c <= '9') {
            return (int) c - '0';
        } else {
            return (int) c - 'A' + 10;
        }
    }
}
