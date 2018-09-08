package com.cxx.properties;

import com.cxx.autowire.Car;

import com.mchange.v2.c3p0.DataSources;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class test {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("bean-properties.xml");

        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        System.out.println(dataSource);


    }
}
