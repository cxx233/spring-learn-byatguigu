package com.cxx.spel;


public class Persion {
    private String name;

    // 应用其他数据
    private Car car ;

    // 引用其他bean中的属性值
    private String city;

    // 引用sple运算符
    private String info;

    @Override
    public String toString() {
        return "Persion{" +
                "name='" + name + '\'' +
                ", car=" + car +
                ", city='" + city + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
