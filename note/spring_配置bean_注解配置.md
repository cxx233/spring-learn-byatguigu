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
>com.cxx.annotation.TestObject@37374a5e
>com.cxx.annotation.controller.UserController@4671e53b
>com.cxx.annotation.service.UserService@2db7a79b
>com.cxx.annotation.reposity.UserRepositoryImpl@6950e31

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
>Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'testObject' available
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
​````
调用：
​```java
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
>com.cxx.annotation.controller.UserController@5afa3c9
>com.cxx.annotation.service.UserService@72035809
>Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'userRepository' available
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
>Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'testObject' available
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
>Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'testObject' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:687)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1213)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:284)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1080)
	at com.cxx.annotation.Main.main(Main.java:13)

###### 基于注解配置Bean 说明

> 组件扫描(component scanning):  Spring 能够从 classpath 下自动扫描, 侦测和实例化具有特定注解的组件.

>特定组件包括:
> - **@Component: 基本注解, 标识了一个受 Spring 管理的组件**
> - **@Respository: 标识持久层组件 **(dao 查询数据库)
> - **@Service: 标识服务层(业务层)组件 **（业务逻辑层）
> - **@Controller: 标识表现层组件 **（控制层）

> 对于扫描到的组件, **Spring 有默认的命名策略:**  使用非限定类名, 第一个字母小写. **也可以在注解中通过 value 属性值标识组件的名称**

> 当在组件类上使用了特定的注解之后, 还需要在 Spring 的配置文件中声明 <context:component-scan> ：
 - **  base-package 属性指定一个需要扫描的基类包，Spring 容器将会扫描这个基类包里及其子包中的所有类.**  
 - ** 当需要扫描多个包时, 可以使用逗号分隔. **
 - ** <context:include-filter> 子节点表示要包含的目标类 **
 - ** <context:exclude-filter> 子节点表示要排除在外的目标类 **
 - <context:component-scan> 下可以拥有若干个  <context:include-filter> 和 <context:exclude-filter> 子节点
 - 如果仅希望扫描特定的类而非基包下的所有类，可使用 resource-pattern 属性过滤特定的类，示例：
 ```xml
 <context:component-scan base-package="com.cxx.annotation"
 resource-pattern="repository/*.class"></context:component-scan>
 ```

> <context:include-filter> 和 <context:exclude-filter> 子节点支持多种类型的过滤表达式：
> ![<context:include-filter> 和 <context:exclude-filter> 子节点支持多种类型的过滤表达式：](../images/context.png)



###### 组件装配：@Autowired和@Resource、@Inject注解

> <context:component-scan> 元素还会自动注册 AutowiredAnnotationBeanPostProcessor 实例（Bean的后置处理器，作用是：在配置bean之前和之后进行操作的）, 该实例可以自动装配具有 **==@Autowired==** 和 **@Resource** 、**@Inject**注解的属性.

根据上面配置：

UserService.java

```java
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void add() {
        System.out.println("UserService add");
        userRepository.save();
    }
}
```

UserController.java

```java
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public void execute() {
        System.out.println("UserController execute...");
        userService.add();
    }
}
```

调用：

```java
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");
        UserController userController = (UserController) ctx.getBean("userController");
        System.out.println(userController);
        userController.execute();
        // 关闭IOC容器
        ctx.close();
```

输出：

>com.cxx.annotation.controller.UserController@32eebfca
>UserController execute...
>UserService add
>UserRepostity save...

###### 组装配置 @Autowired说明：**@Autowired按byType自动注入**

> @Autowired 注解自动装配**具有兼容类型**的单个 Bean属性
>
> - **构造器, 普通字段(即使是非 public), 一切具有参数的方法都可以应用@Authwired 注解**
> - **默认情况下, 所有使用 @Authwired 注解的属性都需要被设置. 当 Spring 找不到匹配的 Bean 装配属性时, 会抛出异常,** ==若某一属性允许不被设置, 可以设置 @Authwired 注解的 required 属性为 false==
> - 默认情况下, 当 IOC 容器里存在多个类型兼容的 Bean 时, 通过类型的自动装配将无法工作. 此时可以在 **@Qualifier** 注解里提供 Bean 的名称. **Spring 允许对方法的入参标注 @Qualifiter 已指定注入 Bean 的名称**
> - @Authwired 注解也可以应用在**数组类型**的属性上, 此时 Spring 将会把所有匹配的 Bean 进行自动装配.
> - @Authwired 注解也可以应用在**集合属性**上, 此时 Spring 读取该集合的类型信息, 然后自动装配所有与之兼容的 Bean. 
> - @Authwired 注解用在 **java.util.Map** 上时, 若该 Map 的键值为 String, 那么 Spring 将自动装配与之 Map 值类型兼容的 Bean, 此时 Bean 的名称作为键值

1.构造器, 普通字段(即使是非 public), 一切具有参数的方法都可以应用@Authwired 注解

具体例子；

```java
	private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
```

2.**默认情况下, 所有使用 @Authwired 注解的属性都需要被设置. 当 Spring 找不到匹配的 Bean 装配属性时, 会抛出异常,** ==若某一属性允许不被设置, 可以设置 @Authwired 注解的 required 属性为 false==

具体例子：

```java
@Service
public class UserService {

    @Autowired(required = false)
    private TestObject testObject;
```

3.默认情况下, 当 IOC 容器里存在多个类型兼容的 Bean 时, 通过类型的自动装配将无法工作. 此时可以在 **@Qualifier** 注解里提供 Bean 的名称. **Spring 允许对方法的入参标注 @Qualifiter 已指定注入 Bean 的名称**

> 说明：由于@Autowired注释是根据byType来进行注入装配的。
>
> 于是乎在下面的例子中：展示的是两个类共同实现同一个接口，并同时配置bean。-》 于是乎，他们两个类是在注入到接口的时候都是该接口的类型



例子：有一个接口

```java
public interface UserRepository {
    void save();
}
```

两个实现类:共同配置bean

第一种情况：

-------

UserRepositoryImpl.java

```java
@Repository
public class UserRepositoryImpl implements UserRepository {
    public void save() {
        System.out.println("UserRepostity save...");
    }
}
```

UserJdbcRepository.java

```java
@Repository
public class UserJdbcRepository implements UserRepository {
    public void save() {
    }
}
```

注入的时候

```java
    @Autowired
    private UserRepository userRepository;
```

这时会报错。

则注入的时候需要修改成精准配置

```java
    @Autowired
    @Qualifier(value = "userJdbcRepository")
    private UserRepository userRepository;
```

首先要明白一点：在配置自动Bean的时候@Repository 如果不设定value值，使用默认的命名策略（使用非限定类名，第一个字母小写）。那么@Qualifier--精准配置一个组件的名称，则不会在@Autowird中进行出错了（@Autowird中根据类的类型进行装配的byType）。@Qualifier(value = "userJdbcRepository") 中的userJdbcRepository是没有配置的。但是在UserJdbcRepository使用了默认命名策略而形成的。

--------------------

第二种情况：

UserRepositoryImpl.java

```java
@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {
    public void save() {
        System.out.println("UserRepostity save...");
    }
}
```

UserJdbcRepository.java

```java
@Repository
public class UserJdbcRepository implements UserRepository {
    public void save() {
    }
}
```

注入的时候

```java
    @Autowired
    private UserRepository userRepository;
```

这时不会出错

调用的时候：

```java
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");
        UserController userController = (UserController) ctx.getBean("userController");
        System.out.println(userController);
        userController.execute();
```

结果：

> com.cxx.annotation.controller.UserController@13eb8acf
> UserController execute...
> UserService add
> userRepositypcom.cxx.annotation.reposity.**UserRepositoryImpl**@51c8530f
> UserRepostity save...

原因：

>@Repository("userRepository")配置的bean的id（命名）是userRepository和  @Autowired自动装配的类的命名是一致的。并且@Repository配置的bean是@Autowired注入的类的实现类，那么@Repository配置的bean可以向上转型，不会报错。就类似于下面代码的情况：
>
>```java
>UserRepository userReposity = (UserRepository) ctx.getBean("userRepository");
>System.out.println(userReposity);
>```
>
>

整一个自动装配的流程我猜测应该是这样：

> 1. @Autowired 自动注入的时候根据类（UserRepository）的类型（UserRepository.class）在IOC容器中来找出是这个类的bean（UserRepository找不到）。
>
> 2. 由于是接口找不到的组件话，那么会在IOC容器中找他的实现类的组件-bean。如果IOC容器中有该接口多个实现类的组件的话，注入的解决方案便是根据注入类的对象（实例）名称（userRepository），来获取IOC中是该接口实现类的组件并且命名为类的对象（实例）名称。以下面来说明
>
>    配置的时候：
>
>    ```java
>    @Repository("userRepository_Name")
>    public class UserRepositoryImpl implements UserRepository 
>    ```
>
>    注入的时候
>
>    ```java
>        @Autowired
>        private UserRepository userRepository_Name;
>    ```
>
>    。否则的话，则会报错说有多个类型的，无法正常匹配并注入。
>
>    ​
>

可参考学习该[链接](https://blog.csdn.net/u013257679/article/details/52295106)的知识

###### 使用 @Resource 或 @Inject 自动装配 Bean

> - Spring 还支持 @Resource 和 @Inject 注解，这两个注解和 @Autowired 注解的功用类似
> - **==@Resource 注解要求提供一个 Bean 名称的属性，若该属性为空，则自动采用标注处的变量或方法名作为 Bean 的名称==**
> - @Inject 和 @Autowired 注解一样也是按类型匹配注入的 Bean， 但没有 reqired 属性
> - 建议使用 @Autowired 注解

