package com.cxx.cycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("自己设置的后置处理器中postProcessBeforeInitialization()方法生效【在初始化方法 前 起作用】了：传输的bean对象为：" + o+"\tbean对象id（名称）为：" + s);
        if (o instanceof Car) {
            System.out.println("后置处理器中现在接收到的对象是Car类的实例，则可以进行对其操作处理（例如顶替、更换等操作，不知道删除可不可以）");
        }
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("自己设置的后置处理器中postProcessAfterInitialization()方法生效【在初始化方法 后 起作用】了：传输的bean对象为：" + o+"\tbean对象id（名称）为：" + s);
        return o;
    }
}
