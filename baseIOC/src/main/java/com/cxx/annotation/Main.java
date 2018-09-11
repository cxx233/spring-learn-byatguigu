package com.cxx.annotation;

import com.cxx.annotation.controller.UserController;
import com.cxx.annotation.reposity.UserRepository;
import com.cxx.annotation.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");
        UserRepository userReposity = (UserRepository) ctx.getBean("userRepository");
        System.out.println(userReposity);
        TestObject testObject = (TestObject) ctx.getBean("testObject");
        System.out.println(testObject);

        UserController userController = (UserController) ctx.getBean("userController");
        System.out.println(userController);
        UserService userService = (UserService) ctx.getBean("userService");
        System.out.println(userService);

        // 关闭IOC容器
        ctx.close();
    }
}
