package com.cxx.beanFactory;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("factory-bean.xml");
        Car car1 = (Car) ctx.getBean("car");
        System.out.println(car1);

        // 关闭IOC容器
        ctx.close();
    }
}
