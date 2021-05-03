package com.company;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Scanner scan = new Scanner(System.in);
        NewBigInteger num1 = new NewBigInteger("555555555555555555555555555555555555");
        NewBigInteger num2 = new NewBigInteger("777777777777777777777777777777777777");
        System.out.println(num1.multiply(num2).toStr() + " multiply");
        System.out.println(num1.multiplyKaratsuba(num2).toStr() + " Karatsuba");

        BigInteger num3 = new BigInteger("555555555555555555555555555555555555");
        BigInteger num4 = new BigInteger("777777777777777777777777777777777777");
        System.out.println(num3.multiply(num4).toString() + " real BigInteger multiply");

        NewBigInteger array = NewBigInteger.createDefaultRandom();
        System.out.println(array.toStr());
        System.out.println(array.mergeSortNumbers().toStr() + " MergeSorted num1 numbers");
        System.out.println(array.toStr());
        System.out.println(array.sortNumbers().toStr() + " defaultSorted num1 numbers");

        /*System.out.println(num.divide(num2).toStr() + " div");
        System.out.println(num.add(num2).toStr() + " add");
        System.out.println(num.subtract(num2).toStr() + " subtract");
        System.out.println(num.mod(num2).toStr() + " mod");*/
    }
}
