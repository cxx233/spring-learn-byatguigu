package com.cxx.aop.prelude;

public class ArithmeticCalculatorImpl implements ArithmeticCalculator {

    public double add(int i, int j) {
        int result = i+j;
        return result;
    }


    public double sub(int i, int j) {
        int result = i-j;
        return result;
    }

    public double mul(int i, int j) {
        int result = i*j;
        return result;
    }


    public double div(int i, int j) {
        int result = i/j;
        return result;
    }
}
