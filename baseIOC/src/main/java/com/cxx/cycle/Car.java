package com.cxx.cycle;

public class Car {
    public Car() {
        System.out.println("Car's Constructor....");
    }

    private String brand;

    public void setBrand(String brand) {
        System.out.println("setBrand...");
        this.brand = brand;
    }

    public void initCar() {
        System.out.println("Car init...");
    }

    public void destroyCar() {
        System.out.println("Car destroy...");
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                '}';
    }
}
