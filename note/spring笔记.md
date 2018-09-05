# spring笔记



> 本笔记是观看尚硅谷的佟刚老师的《尚硅谷spring4》视频

## spring 基础理解

1. spring是什么：

> - 轻量级：Spring 是非侵入性的 - 基于 Spring 开发的应用中的对象可以不依赖于Spring 的 API
> - 依赖注入(DI --- dependency injection、IOC)
> - 面向切面编程(AOP --- aspect oriented programming)容器: Spring 是一个容器, 因为它包含并且管理应用对象的生命周期
> - 框架: Spring 实现了使用简单的组件配置组合成一个复杂的应用. 在 Spring 中可以使用 XML 和 Java 注解组合这些对象
> - 一站式：在 IOC 和 AOP 的基础上可以整合各种企业应用的开源框架和优秀的第三方类库 （实际上 Spring 自身也提供了展现层的 SpringMVC 和 持久层的 Spring JDBC）

### spring 模块

![模块图](../images/spring模块图.png)



### spring 的IOC&DI概念

> - IOC(Inversion of Control)：其思想是**==反转资源获取的方向==**. 传统的资源查找方式要求组件向容器发起请求查找资源. 作为回应, 容器适时的返回资源. 而应用了 IOC 之后, 则是==**容器主动地将资源推送给它所管理的组件, 组件所要做的仅是选择一种合适的方式来接受资源.**== 这种行为也被称为查找的被动形式 =》类似于需要一个鸡蛋买回来放入篮子中，但是我把篮子放在门口（需要标注需要的东西），然后有人就会自动的把需要的鸡蛋（内容）放入到篮子中，这样便可以用这个鸡蛋了（内容）。
> - DI(Dependency Injection)— IOC 的另一种表述方式：==即组件以一些预先定义好的方式**(例如: setter 方法)**接受来自如容器的资源注入==. 相对于 IOC 而言，这种表述更直接

### IOC的前生

#### 1. 分离接口与实现

![分离接口与实现](../images/IOC前生_分离接口与实现.png)

​	采用自己的语言描述：将报表用一接口定义（ReprotGenerator），pdf的报表生成器与html的报表生成器分别实现该报表生成器的接口，而报表服务层（ReportService）则用如下的方法进行调用-》从而实现说接口的分离。

```
ReportGenerator rg = new PdfReportGenerator();
ReportGenerator rg = new HtmlReportGenerator();
```



#### 2. 采用工厂模式

![工厂模式](../images/IOC前生_工厂设计模式.png)

​	采用自己的语言简单描述：在分离接口基础上，由工厂（ReprotGeneratorFactory）去生成Report Generator对应的实现类，再在ReportService中进行调用

#### 3. 采用反转控制

![采用反转控制](../images/IOC前生_反转控制.png)



### IOC&DI概述

#### 配置 bean -- 重点

>- 配置形式：基于 XML 文件的方式；基于注解的方式
>- Bean 的配置方式：通过全类名（反射）、通过工厂方法（静态工厂方法 & 实例工厂方法）、FactoryBean
>- IOC 容器 BeanFactory & ApplicationContext 概述
>- 依赖注入的方式：属性注入；构造器注入
>- 注入属性值细节
>- 自动转配
>- bean 之间的关系：继承；依赖
>- bean 的作用域：singleton；prototype；WEB 环境作用域
>- 使用外部属性文件
>- spEL 
>- IOC 容器中 Bean 的生命周期
>- Spring 4.x 新特性：泛型依赖注入

结合具体例子：

​	背景：创建一个Service层的对象，然后将其配置到xml文件中（通过全类名-反射）。

1. 1 spring.xml文件 -- 放置在Classpath下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="fadacai" class="com.cxx.service.FDC" >
        <property name="fdc" value="一夜暴富"></property>
    </bean>

</beans>
```

1.2 Service层

```java
package com.cxx.service;

public class FDC {

    private String fdc;

    public String getFdc() {
        return fdc;
    }

    public void setFdc(String fdc) {
        this.fdc = fdc;
    }

    public void service() {
        System.out.println("成长-不可能一帆风顺-但不可放弃-努力吧-发大财！");
    }
}

```

1.3 Controller 调用

```java
package com.cxx.Controller;

import com.cxx.service.FDC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class controller {

    public static void main(String[] args) {
        // 1. 创建Spring的IOC容器 ApplicationContext是一个接口
      	// ApplicationContext 代表IOC容器
      	// ClassPathXmlApplicationContext：是ApplicationContext的实现类，该实现类从类路径下加载的配置文件
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        // 2. 从容器中获取Bean对象
      	// 利用id定位到IOC容器中的bean
        FDC fdc = (FDC) ctx.getBean("fadacai");
		// 如果利用类型返回IOC容器中的Bean，但要求的是IOC容器中必须只能由一个该类型的Bean 如下
      	// FDC fdc = (FDC) ctx.getBean(FDC.class);
      
        // 3. 调用对象方法
        System.out.println(fdc.getFdc());
        fdc.service();

    }
}

```







##### 1. 在xml文件中配置

```xml
    <bean id="fadacai" class="com.cxx.service.FDC" > -- bean 通过全类名进行配置 
        <property name="fdc" value="一夜暴富"></property> -- name -- 是类中的setFdc()方法进行配置FDC属性的内容 -- 俗称赋值-》DI依赖注入的内容
    </bean>
```

>注释：
>
>-id：Bean的名称
>
>  - 在IOC容器是唯一的
>  - 若id没有指定，Spring自动将权限定性类名作为Bean的名字
>  - id可以指定多个名字，名字之间可用逗号、分号、空格分隔



##### 2. spring容器

​	在SpringIOC容器中读取Bean配置并创建Bean实例之前，必须要对Spring容器进行实例化如下：

```java
ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
```

​	也就是说：只有在容器实例化后，才可以用IOC容器里获取Bean的实例并使用。

spring提供两种类型的IOC容器

- BeanFactory: IOC容器的基本实现
- ApplicationContext：提供了更多的高级特性。是BeanFactory的子接口

> BeanFactory 是Srping框架的基础设施，面向Spring本身；
>
> ApplicationContext：面向使用Spring框架的开发者，**几乎所有的应用场合都直接使用ApplicationContext而非底层的BeanFactory。**
>
> 无论使用何用方式，配置文件时是相同的。

ApplicationContext的实现关系：

![ApplicationContext的实现以及子类](../images/Application的继承管理.png)

###### ApplicationContext内容

> 主要实现类：
>
> - **ClassPathXmlApplicationContext**：从 类路径下加载配置文件
> - FileSystemXmlApplicationContext: 从文件系统中加载配置文件

![IOC容器关系UML图](../images/IOC容器关系.png)

>由上图（上上图）可得：ClassPathxxx和FileSystemxxx最终是实现了ConfigurableApplicationContext接口，ConfigurableApplicationContext是继承了ApplicationContext接口的。
>
>ConfigurableApplicationContext 扩展于 ApplicationContext，新增加两个主要方法：refresh() 和 **close**()， 让 ApplicationContext 具有启动、刷新和关闭上下文的能力

> **ApplicationContext 在初始化上下文时就实例化所有单例的 Bean。**

> WebApplicationContext 是专门为 WEB 应用而准备的，它允许从相对于 WEB 根目录的路径中完成初始化工作