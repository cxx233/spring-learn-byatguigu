package com.cxx.beanFactory;

import org.springframework.beans.factory.FactoryBean;
// 自定义的FactoryBean 需要实现FactoryBean 接口
public class CarFactory implements FactoryBean<Car> {

    private String brand ;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    //    返回bean的对象
    public Car getObject() throws Exception {
        return new Car("jx",brand,500000f);
    }

//    返回的bean 的类型
    public Class<?> getObjectType() {
        return Car.class;
    }

//    是否单例
    public boolean isSingleton() {
        return false;
    }
}
