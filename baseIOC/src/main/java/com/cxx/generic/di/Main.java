package com.cxx.generic.di;

import com.cxx.generic.di.service.BaseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-generter.xml");
        BaseService service1 = (BaseService) ctx.getBean("userService");
        service1.add();

        BaseService service2 = (BaseService) ctx.getBean("organizationService");
        service2.add();

    }
}
