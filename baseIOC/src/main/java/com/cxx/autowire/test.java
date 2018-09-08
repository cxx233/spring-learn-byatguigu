package com.cxx.autowire;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-relation.xml");
//        Address address = (Address) ctx.getBean("address");
//        System.out.println(address);

//        Address address2 = (Address) ctx.getBean("address2");
//        System.out.println(address2);

        Car car = (Car) ctx.getBean("car");
        System.out.println(car);

        Persion persion = (Persion) ctx.getBean("persion");
        System.out.println(persion);
    }
}
