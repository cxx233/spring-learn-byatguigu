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

##### 3 从IOC容器中获取Bean

调用ApplicationContext的getBean()方法



##### 4. 依赖注入的方式 

###### 4.0 理解

这里的依赖注入我理解的是：类中的属性可以通过setter方式（属性注入）、以及通过构造器进行注入到该bean实例中

> - 属性注入
> - 构造器注入
> - 工厂方法注入（很少使用，不推荐）

###### 4.1 属性注入 -- 一定要有setter方法

```xml
   <bean id="fadacai" class="com.cxx.service.FDC" > -- bean 通过全类名进行配置 
        <property name="fdc" value="一夜暴富"></property> -- name -- 是类中的setFdc()方法进行配置FDC属性的内容 -- 俗称赋值-》DI依赖注入的内容
    </bean>
```

里面的<property>标识中，name里面的内容表示要注入的SetXXX方法（注意的是，使用的是驼峰标识）。value表示要赋入的值。

- 属性注入使用 <property> 元素, 使用 name 属性指定 Bean 的属性名称，value 属性或 <value> 子节点指定属性值 

>属性注入即通过 **setter** 方法注入Bean 的属性值或依赖的对象
>
>属性注入是实际应用中最常用的注入方式



###### 4.2 构造方法注入 -- 一定要有对应的构造器

使用的是<constructor-arg> -- 使用构造器注入属性值可以指定参数的位置和参数的类型（可混合使用）以区分重载的构造器！

【实例2】使用实例：背景-有一个Car类，在IOC容器中实例化后，进行注入。

Car.class

```java
package com.cxx.entity;

public class Car {
    private String company;
    private String brand;
    private int maxSpeed;
    private float price;

  // setter和getter方法()
    public Car(String company, String brand, int maxSpeed) {
        this.company = company;
        this.brand = brand;
        this.maxSpeed = maxSpeed;
    }
    public Car(String company, String brand, float price) {
        this.company = company;
        this.brand = brand;
        this.price = price;
    }
}

```

配置文件：按照索引来配置的。

```xml
    <bean id="car1" class="com.cxx.entity.Car">
        <constructor-arg value="jx" index="0"></constructor-arg>
        <constructor-arg value="bama" index="1"></constructor-arg>
        <constructor-arg value="120" index="2"></constructor-arg>
    </bean>


    <bean id="car2" class="com.cxx.entity.Car">
        <constructor-arg value="cxx" index="0"></constructor-arg>
        <constructor-arg value="fll" index="1"></constructor-arg>
        <constructor-arg value="5200.0" index="2"></constructor-arg>
    </bean>
```

调用：明明配置一个是整数、另外一个是浮点的，但是为什么都会配置到浮点型的属性中呢？所以上面的索引不太合适。

```java
        // 1. 创建Spring的IOC容器 ApplicationContext是一个接口
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        Car car1 = (Car) ctx.getBean("car1");
        System.out.println(car1);

        Car car2 = (Car) ctx.getBean("car2");
        System.out.println(car2);
```

输出：

>Car{company='jx', brand='bama', maxSpeed=0, **price=120.0**}
>Car{company='cxx', brand='fll', maxSpeed=0, **price=5200.0**}

--》 从而引出用构造器中配置**参数类型（区分重载的方法）** 

```xml
    <bean id="car1" class="com.cxx.entity.Car">
        <constructor-arg value="jx" type="java.lang.String"></constructor-arg>
        <constructor-arg value="bama" type="java.lang.String"></constructor-arg>
        <constructor-arg value="120" type="int"></constructor-arg>
    </bean>

    <bean id="car2" class="com.cxx.entity.Car">
        <constructor-arg value="cxx" type="java.lang.String"></constructor-arg>
        <constructor-arg value="fll" type="java.lang.String"></constructor-arg>
        <constructor-arg value="5200.0" type="float"></constructor-arg>
    </bean>
```

输出结果：

>Car{company='jx', brand='bama', **maxSpeed=120**, price=0.0}
>Car{company='cxx', brand='fll', maxSpeed=0, **price=5200.0**}



##### 5. 注入属性值细节

###### 5.1 使用字面值

>字面值：可用字符串表示的值，可以通过 <value> 元素标签或 value 属性进行注入。
>
>**基本数据类型及其封装类、String** 等类型都可以采取字面值注入的方式 -- 会自动转换，例如上方的value="5200.0"是String，转换为float
>
>若字面值中包含特殊字符，可以使用 **<![CDATA[]]>** 把字面值包裹起来。



###### 5.2 引用其他Bean

背景如【实例2】中，增加人这个class

```java
package com.cxx.entity;

public class Persion {
    private String name;
    private int age;
    private Car car ;
// getter和setter方法

    @Override
    public String toString() {
        return "Persion{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", car=" + car +
                '}';
    }
}

```

xml中配置

```xml
    <bean id="car1" class="com.cxx.entity.Car">
        <constructor-arg value="jx" type="java.lang.String"></constructor-arg>
        <constructor-arg value="bama" type="java.lang.String"></constructor-arg>
        <constructor-arg value="120" type="int"></constructor-arg>
    </bean>


    <bean id="car2" class="com.cxx.entity.Car">
        <constructor-arg value="cxx" type="java.lang.String"></constructor-arg>
        <constructor-arg value="fll" type="java.lang.String"></constructor-arg>
        <constructor-arg value="5200.0" type="float"></constructor-arg>
    </bean>

    <bean class="com.cxx.entity.Persion" id="persion">
        <property name="name" value="cxx"></property>
        <property name="age" value="18"></property>
        <!--可以使用property的ref属性建立bean之间的应用关系-->
        <property name="car" ref="car1"></property>
    </bean>
```

调用：

```java
      Persion persion = (Persion) ctx.getBean("persion");
        System.out.println(persion);
```

输入：

>Persion{name='cxx', age=18, car=Car{company='jx', brand='bama', maxSpeed=120, price=0.0}}

说明：

> - 组成应用程序的 Bean 经常需要相互协作以完成应用程序的功能. 要使 Bean 能够相互访问, 就必须在 Bean 配置文件中指定对 Bean 的引用
> - 在 Bean 的配置文件中, 可以通过 <ref> 元素或 ref  属性为 Bean 的属性或构造器参数指定对 Bean 的引用. 
> - 也可以在**属性或构造器里包含 Bean 的声明**, 这样的 Bean 称为**内部 Bean**

内部Bean的样例

```xml
    <bean class="com.cxx.entity.Persion" id="JX">
        <property name="name" value="JX"></property>
        <property name="age" value="18"></property>
        <!--可以使用使用内部bean，内部bean并不需要设置太多东西，只需要一个全类名就好。因为内部bean只会用于JX的这个实例中，并不会用于其他的实例上-->
        <property name="car">
            <bean class="com.cxx.entity.Car">
                <property name="company" value="Ford"/>
                <property name="brand" value="Changan"/>
                <property name="price" value="120000"/>
            </bean>
        </property>
    </bean>
```

调用：

```JAVA
        Persion JX = (Persion) ctx.getBean("JX");
        System.out.println(JX);
```

输出：

> Persion{name='JX', age=18, car=Car{company='Ford', brand='Changan', maxSpeed=0, price=120000.0}}

内部Bean的说明：

> - 当 Bean 实例仅仅给一个特定的属性使用时, 可以将其声明为内部 Bean. 内部 Bean 声明直接包含在 <property> 或 <constructor-arg> 元素里, **不需要设置任何 id 或 name 属性**
> - 内部 Bean 不能使用在任何其他地方

###### 5.3 注入参数详解：null 值和级联属性

null值

开启构造器

```java
    public Persion(String name, int age, Car car) {
        this.name = name;
        this.age = age;
        this.car = car;
    }

    public Persion() {
    }
```

配置文件：

```xml
    <bean class="com.cxx.entity.Persion" id="FDC">
        <constructor-arg value="FDC"></constructor-arg>
        <constructor-arg value="19"></constructor-arg>
        <constructor-arg ><null/></constructor-arg>
    </bean>
```



级联属性

> 和 Struts、Hiberante 等框架一样，Spring 支持级联属性的配置。

配置文件

```xml
    <bean class="com.cxx.entity.Persion" id="FDC">
        <constructor-arg value="FDC"></constructor-arg>
        <constructor-arg value="19"></constructor-arg>
        <constructor-arg ref="car1"></constructor-arg>
        <!--car里面的price属性必须得有setter方法。并且在car1这个实例中的这个属性都会修改为3000-->
        <!--为级联属性赋值 注意：属性需要先初始化后才可以为级联赋值，否会会有异常-->
        <property name="car.price" value="3000"></property>
    </bean>
```

调用：

```java
        Persion persion = (Persion) ctx.getBean("persion");
        System.out.println(persion);

        Persion JX = (Persion) ctx.getBean("JX");
        System.out.println(JX);

        Persion FDC = (Persion) ctx.getBean("FDC");
        System.out.println(FDC);
```

输出：

>Persion{name='cxx', age=18, car=Car{company='jx', brand='bama', maxSpeed=120, **price=3000.0**}}
>Persion{name='JX', age=18, car=Car{company='Ford', brand='Changan', maxSpeed=0, price=120000.0}}
>Persion{name='FDC', age=19, car=Car{company='jx', brand='bama', maxSpeed=120, **price=3000.0**}}



###### 5.4 集合属性

 5.4.1List实例

实体类：

```java
package com.cxx.entity.collection;
import com.cxx.entity.Car;
import java.util.List;
public class RichPersion {
    private String name;
    private int age;
    private List<Car> car ; // 主要是里的list对那个下方的list元素
	// setter和getter方法
}

```

配置文件：

```xml
    <!--测试集合属性-->
    <bean id="YK" class="com.cxx.entity.collection.RichPersion">
        <property name="name" value="YK"/>
        <property name="age" value="20"/>
        <property name="car">
            <!--使用list对应的list集合实行-->
            <list>
                <ref bean="car1"></ref>
                <ref bean="car2"/>
            </list>
        </property>
    </bean>
```

测试:

```java
  com.cxx.entity.collection.RichPersion YK = (com.cxx.entity.collection.RichPersion) ctx.getBean("YK");
        System.out.println(YK);
```

输出：

>Persion{name='YK', age=20, car=[Car{company='jx', brand='bama', maxSpeed=120, price=3000.0}, Car{company='cxx', brand='fll', maxSpeed=0, price=5200.0}]}

说明：

List和Set的说明

>- 在 Spring中可以通过一组内置的 xml 标签(例如: <list>, <set> 或 <map>) 来配置集合属性.
>- 配置 java.util.List 类型的属性, 需要指定 <list>  标签, 在标签里包含一些元素. 这些标签可以通过 <value> 指定简单的常量值, 通过 <ref> 指定对其他 Bean 的引用. 通过<bean> 指定内置 Bean 定义. 通过 <null/> 指定空元素. 甚至可以内嵌其他集合.
>- **数组的定义和 List 一样, 都使用 <list>**
>- 配置 java.util.Set 需要使用 <set> 标签, 定义元素的方法与 List 一样.

Map的说明

> - Java.util.Map 通过 <map> 标签定义, <map> 标签里可以使用多个 <entry> 作为子标签. 每个条目包含一个键和一个值. 
> - **必须在 <key> 标签里定义键**
> - 因为键和值的类型没有限制, 所以可以自由地为它们指定 <value>, <ref>, <bean> 或 <null> 元素. 
> - **可以将 Map 的键和值作为 <entry> 的属性定义: 简单常量使用 key 和 value 来定义; Bean 引用通过 key-ref 和 value-ref 属性定义**
> - 使用 <props> 定义 **java.util.Properties**, 该标签使用多个 <prop> 作为子标签. 每个 <prop> 标签必须定义 key 属性. 

5.4.2 Map的实例

1. 实体类

   ```java
   package com.cxx.entity.collection;

   import com.cxx.entity.Car;

   import java.util.List;
   import java.util.Map;

   public class MoreRichPersion {
       private String name;
       private int age;
       private Map<String,Car> car ;
   	// setter和getter
   }

   ```

   2.配置xml文件

   ```xml
       <!--测试Map集合-->
       <bean id="GDK" class="com.cxx.entity.collection.MoreRichPersion">
           <property name="name" value="XDD"/>
           <property name="age" value="19"/>
           <property name="car">
               <!--使用map 节点以及map的entry子节点配置Map类型的成员变量-->
               <map>
                   <entry key="AA" value-ref="car1"/>
                   <entry key="BB" value-ref="car2"/>
               </map>
           </property>
       </bean>
   ```

   测试：

   ```java
           MoreRichPersion MRP = (MoreRichPersion) ctx.getBean("GDK");
           System.out.println(MRP);
   ```

   输出：

   >Persion{name='XDD', age=19, car={AA=Car{company='jx', brand='bama', maxSpeed=120, price=3000.0}, BB=Car{company='cxx', brand='fll', maxSpeed=0, price=5200.0}}}

   ​

   5.4.3 Properties实例

   实体类：

   ```java
   package com.cxx.entity.collection;
   import java.util.Properties;
   public class DBProperties {
       private Properties properties;
    //setter与getter
   }
   ```

   配置文件：

   ```xml
       <!--测试PropetiesBean-->
       <bean id="properties" class="com.cxx.entity.collection.DBProperties">
           <property name="properties">
               <!--使用props和prop子节点为Properties属性赋值-->
               <props>
                   <prop key="user">user</prop>
                   <prop key="password">123</prop>
                   <prop key="jdbcUrl">jdbc:mysql:///test</prop>
                   <prop key="driverClass">com.mysql.jdbc.Driver</prop>
               </props>
           </property>
       </bean>
   ```

   调用：

   ```java
           DBProperties properties = (DBProperties) ctx.getBean("properties");
           System.out.println(properties);
   ```

   输出：

   >DBProperties{properties={driverClass=com.mysql.jdbc.Driver, user=user, password=123, jdbcUrl=jdbc:mysql:///test}}

   ​

   ###### 5.5 使用 utility scheme 定义集合

   一句话：配置单例的集合bean，供多个bean进行应用

   > - 缘由：使用基本的集合标签定义集合时, 不能将集合作为独立的 Bean 定义, 导致其他 Bean 无法引用该集合, 所以无法在不同 Bean 之间共享集合.
   > - 解决方案：可以使用 util schema 里的集合标签定义独立的集合 Bean. 需要注意的是, 必须在 <beans> 根元素里添加 util schema 定义

   实例：

   配置文件xml

   注意头:

   > <beans xmlns="http://www.springframework.org/schema/beans"       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"        **xmlns:util="http://www.springframework.org/schema/util"**       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd  http://www.springframework.org/schema/util  http://www.springframework.org/schema/util/spring-util-4.0.xsd ">

   ```xml
       <!--配置单例的集合bean，以供多个bean进行引用，需要导入util命名空间，在idea中打出<util:后Alt+Enter便可自动导入了-->
       <util:list id = "cars">
           <ref bean="car1"/>
           <ref bean="car2"/>
       </util:list>

       <bean class="com.cxx.entity.collection.RichPersion" id="rp">
           <property name="name" value="YK"/>
           <property name="age" value="20"/>
           <property name="car" ref="cars"></property>
       </bean>
   ```

   调用：

   ```java
           RichPersion rp = (RichPersion) ctx.getBean("rp");
           System.out.println(rp);
   ```

   输出：

   >Persion{name='rp', age=20, car=[Car{company='jx', brand='bama', maxSpeed=120, price=3000.0}, Car{company='cxx', brand='fll', maxSpeed=0, price=5200.0}]}

   ​

