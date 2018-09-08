package com.cxx.Controller;

import com.cxx.entity.Car;
import com.cxx.entity.Persion;
import com.cxx.entity.collection.DBProperties;
import com.cxx.entity.collection.MoreRichPersion;
import com.cxx.entity.collection.RichPersion;
import com.cxx.service.FDC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class controller {

    public static void main(String[] args) {
        // 1. 创建Spring的IOC容器 ApplicationContext是一个接口
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

//        // 2. 从容器中获取Bean对象
//        FDC fdc = (FDC) ctx.getBean("fadacai");
//
//
//        // 3. 调用对象方法
//        System.out.println(fdc.getFdc());
//        fdc.service();

//        Car car1 = (Car) ctx.getBean("car1");
//        System.out.println(car1);
//        Car car2 = (Car) ctx.getBean("car2");
//        System.out.println(car2);

//        Persion persion = (Persion) ctx.getBean("persion");
//        System.out.println(persion);
//
//        Persion JX = (Persion) ctx.getBean("JX");
//        System.out.println(JX);
//
//        Persion FDC = (Persion) ctx.getBean("FDC");
//        System.out.println(FDC);

//        com.cxx.entity.collection.RichPersion YK = (com.cxx.entity.collection.RichPersion) ctx.getBean("YK");
//        System.out.println(YK);
//
//        MoreRichPersion MRP = (MoreRichPersion) ctx.getBean("GDK");
//        System.out.println(MRP);
//
//        DBProperties properties = (DBProperties) ctx.getBean("properties");
//        System.out.println(properties);
//
//        RichPersion rp = (RichPersion) ctx.getBean("rp");
//        System.out.println(rp);

        RichPersion qian = (RichPersion) ctx.getBean("YZ");
        System.out.println(qian);
    }
}
