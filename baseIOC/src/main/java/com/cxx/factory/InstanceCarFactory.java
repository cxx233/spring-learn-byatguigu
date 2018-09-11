package com.cxx.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 实例工厂方法：实例工厂的方法，即先需要创建工厂本身，再调用工厂的实例方法来返回bean的实例。
 */
public class InstanceCarFactory {
    private  Map<String,Car> cars =null;

    public InstanceCarFactory(){
        cars= new HashMap<String, Car>();
        cars.put("Ford",new Car("Ford","MENGQ X5",600000f));
        cars.put("BenChi",new Car("BenChi","BENCHI e370", 1200000.0f));
    }

    public Car getCar(String name) {
        return cars.get(name);
    }

}
