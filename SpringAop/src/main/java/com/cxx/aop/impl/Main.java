package com.cxx.aop.impl;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicaionContext.xml");

//        ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) ctx.getBean("arithmeticCalculatorImpl");
        ArithmeticCalculator arithmeticCalculator =ctx.getBean(ArithmeticCalculator.class);

        double result = arithmeticCalculator.add(5,6);
        System.out.println(result);

         result = arithmeticCalculator.mul(4,6);
        System.out.println(result);
    }
}
