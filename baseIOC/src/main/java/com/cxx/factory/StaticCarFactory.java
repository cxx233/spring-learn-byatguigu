package com.cxx.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 静态工厂方法：直接某一个类的静态方法就可以返回Bean 的实例
 */
public class StaticCarFactory {
    private static Map<String,Car> cars = new HashMap<String, Car>();

    static {
        cars.put("Audi",new Car("jx","audi Q7",700000f));
        cars.put("BMW",new Car("cxx","bmw m5", 800000.0f));
    }

    public static Car getCar(String name) {
        return cars.get(name);
    }

}
