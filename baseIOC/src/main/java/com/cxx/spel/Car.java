package com.cxx.spel;

public class Car {

    private String brand;

    private float price;

    private float typePerimeter; // 轮胎半径


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTypePerimeter() {
        return typePerimeter;
    }

    public void setTypePerimeter(float typePerimeter) {
        this.typePerimeter = typePerimeter;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                ", typePerimeter=" + typePerimeter +
                '}';
    }
}
