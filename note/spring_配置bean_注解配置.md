##### 基于注解的方式（基于注解配置 Bean；基于注解来装配 Bean 的属性）
实例：
创建一个持久层接口、持久层实例、服务层的实例、显示层的实例
持久层接口:
UserRepository.class
```java
package com.cxx.annotation.reposity;

public interface UserRepository {
    void save();
}
```
持久层接口的实现类
UserRepositoryImpl.class
```java
package com.cxx.annotation.reposity;
import org.springframework.stereotype.Repository;
@Repository(value = "userRepository")
public class UserRepositoryImpl implements UserRepository {
    public void save() {
        System.out.println("UserRepostity save...");
    }
}
```
服务层实现：
```java
package com.cxx.annotation.service;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    public void add() {
        System.out.println("UserService add");
    }
}
```
显示层实现
```java
package com.cxx.annotation.controller;
import org.springframework.stereotype.Controller;
@Controller
public class UserController {
    public void execute() {
        System.out.println("UserController execute...");
    }
}
```
测试类：
TestObject.class
```java
package com.cxx.annotation;
import org.springframework.stereotype.Component;
@Component
public class TestObject {
}
```

调用
```java
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");

        TestObject testObject = (TestObject) ctx.getBean("testObject");
        System.out.println(testObject);

        UserController userController = (UserController) ctx.getBean("userController");
        System.out.println(userController);

        UserService userService = (UserService) ctx.getBean("userService");
        System.out.println(userService);

        UserRepository userReposity = (UserRepository) ctx.getBean("userRepository");
        System.out.println(userReposity);

        // 关闭IOC容器
        ctx.close();
```

结果：
>信息: Loading XML bean definitions from class path resource [beans-annotation.xml]
com.cxx.annotation.TestObject@37374a5e
com.cxx.annotation.controller.UserController@4671e53b
com.cxx.annotation.service.UserService@2db7a79b
com.cxx.annotation.reposity.UserRepositoryImpl@6950e31

###### 如果仅希望扫描特定的类而非基包下的所有类，可使用 resource-pattern 属性过滤特定的类

spring配置文件 -- 只扫描repository包下的类
```XML
<!--指定Spring IOC容器扫描的包
 可以通过resource-pattern 指定扫描的资源
-->
<context:component-scan base-package="com.cxx.annotation"
resource-pattern="repository/*.class"></context:component-scan>
```
调用:
```java
ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");

        UserRepository userReposity = (UserRepository) ctx.getBean("userRepository");
        System.out.println(userReposity);

        TestObject testObject = (TestObject) ctx.getBean("testObject");
        System.out.println(testObject);

        UserController userController = (UserController) ctx.getBean("userController");
        System.out.println(userController);

        UserService userService = (UserService) ctx.getBean("userService");
        System.out.println(userService);

        // 关闭IOC容器
        ctx.close();
```
输出：
>com.cxx.annotation.reposity.UserRepositoryImpl@eafc191
Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'testObject' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:687)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1213)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:284)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1080)
	at com.cxx.annotation.Main.main(Main.java:16)

###### <context:exclude-filter> 子节点表示要排除在外的目标类
名词：
1. 组件 == bean
2. 指定表达式 == 注解

**这里展示的是**

**type类型是annotation的**

配置spring的xml文件
```XML
<!--
context:exclude-filter 子节点指定排除那些指定表达式的组件，
这里的排除是说将组件为Repository的annotation给排除掉，将这类型的所配置的Bean都排除都不生效了
-->
<context:component-scan base-package="com.cxx.annotation">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Repository"/>
</context:component-scan>
```

调用：
```java
ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");
TestObject testObject = (TestObject) ctx.getBean("testObject");
 System.out.println(testObject);
UserController userController = (UserController) ctx.getBean("userController");
 System.out.println(userController);
UserService userService = (UserService) ctx.getBean("userService");
 System.out.println(userService);
UserRepository userReposity = (UserRepository) ctx.getBean("userRepository");
 System.out.println(userReposity);
// 关闭IOC容器
 ctx.close();
```

输出： 表示的是不认识有Repository注解的组件（bean）
>Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'userRepository' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:687)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1213)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:284)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1080)
	at com.cxx.annotation.Main.main(Main.java:17)
com.cxx.annotation.TestObject@462d5aee
com.cxx.annotation.controller.UserController@69b0fd6f
com.cxx.annotation.service.UserService@757942a1

**这里展示的是**

**assignable**
配置spring的xml文件
```XML

<!--
        这个排除的类型是：assignable。指定确定的类以及实现类是无法正常形成bean（不能正常形成组件）
        expression：的内容是类的接口，表示该类以及类的实现都不会正常放入容器中
        expression：的内容是抽象类的话，还没实现。可以尝试
-->
<context:component-scan base-package="com.cxx.annotation">
        <context:exclude-filter type="assignable"  expression="com.cxx.annotation.reposity.UserRepository"/>
</context:component-scan>
````
调用：
```java
ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");
        TestObject testObject = (TestObject) ctx.getBean("testObject");
        System.out.println(testObject);
        UserController userController = (UserController) ctx.getBean("userController");
        System.out.println(userController);
        UserService userService = (UserService) ctx.getBean("userService");
        System.out.println(userService);
        UserRepository userReposity = (UserRepository) ctx.getBean("userRepository");
        System.out.println(userReposity);
        // 关闭IOC容器
        ctx.close();
```
输出： 前三个是正常的，因为UserRepository这个接口被排除了，其实现类也无法放置到容器中
>com.cxx.annotation.TestObject@72cc7e6f
com.cxx.annotation.controller.UserController@5afa3c9
com.cxx.annotation.service.UserService@72035809
Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'userRepository' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:687)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1213)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:284)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1080)
	at com.cxx.annotation.Main.main(Main.java:17)



###### <context:include-filter> 子节点表示要包含的目标类(不包含的类则不会是生效)
**这里展示的是**

**type类型是annotation的**

配置spring的xml文件
```XML
<!--
          context:include-filter 子节点指定包含那些表达式的组件，该子节点需要use-default-filters（需要改为false）配合使用
          use-default-filters:表示的是使用默认的filters。如果值为true，则下方的context:include-filter 不生效，因为都使用了默认的filters，那下方就不会再用了
          只有use-default-filters的值为false的话，那么下方的包含的组件才会生效
   -->
  <context:component-scan base-package="com.cxx.annotation"
          use-default-filters="false">
          <context:include-filter type="annotation"  expression="org.springframework.stereotype.Repository"/>
  </context:component-scan>
  ```
调用：
```java
ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");
        TestObject testObject = (TestObject) ctx.getBean("testObject");
        System.out.println(testObject);
        UserController userController = (UserController) ctx.getBean("userController");
        System.out.println(userController);
        UserService userService = (UserService) ctx.getBean("userService");
        System.out.println(userService);
        UserRepository userReposity = (UserRepository) ctx.getBean("userRepository");
        System.out.println(userReposity);
        // 关闭IOC容器
        ctx.close();
    }
```
输出：
>com.cxx.annotation.reposity.UserRepositoryImpl@1b26f7b2
Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'testObject' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:687)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1213)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:284)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1080)
	at com.cxx.annotation.Main.main(Main.java:13)

  **这里展示的是**

  **type类型是assignable的**

配置spring的xml文件
```XML

        <!--
                这个只包含的类型是：assignable。com.cxx.annotation.reposity.UserRepository只有该类（接口）的以及实现类才会放置在容器中。在com.cxx.annotation这个扫描包的前提下
        -->
        <context:component-scan base-package="com.cxx.annotation" use-default-filters="false">
                <context:include-filter type="assignable"  expression="com.cxx.annotation.reposity.UserRepository"/>
        </context:component-scan>
```
调用
```java
ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");
UserRepository userReposity = (UserRepository) ctx.getBean("userRepository");
System.out.println(userReposity);
TestObject testObject = (TestObject) ctx.getBean("testObject");
System.out.println(testObject);

UserController userController = (UserController) ctx.getBean("userController");
System.out.println(userController);
UserService userService = (UserService) ctx.getBean("userService");
System.out.println(userService);

// 关闭IOC容器
ctx.close();
```
输出：
>com.cxx.annotation.reposity.UserRepositoryImpl@1b26f7b2
Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'testObject' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:687)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1213)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:284)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1080)
	at com.cxx.annotation.Main.main(Main.java:13)
