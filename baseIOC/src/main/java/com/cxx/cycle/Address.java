package com.cxx.cycle;

public class Address {

    private String street;

    public void setStreet(String street) {
        this.street = street;
    }

    public Address() {
        System.out.println("Address's 构造器");
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                '}';
    }

    public void addressInit() {
        System.out.println("Address中得方法addressInit被调用了");
    }

    public void addressDestroy() {
        System.out.println("Address中的方法addressDestroy被调用了");
    }
}
