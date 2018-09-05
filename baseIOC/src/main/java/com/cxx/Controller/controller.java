package com.cxx.Controller;

import com.cxx.service.FDC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class controller {

    public static void main(String[] args) {
        // 1. 创建Spring的IOC容器 ApplicationContext是一个接口
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        // 2. 从容器中获取Bean对象
        FDC fdc = (FDC) ctx.getBean("fadacai");

        // 3. 调用对象方法
        System.out.println(fdc.getFdc());
        fdc.service();

    }
}
