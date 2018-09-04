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

> IOC(Inversion of Control)：其思想是**==反转资源获取的方向==**. 传统的资源查找方式要求组件向容器发起请求查找资源. 作为回应, 容器适时的返回资源. 而应用了 IOC 之后, 则是==**容器主动地将资源推送给它所管理的组件, 组件所要做的仅是选择一种合适的方式来接受资源.**== 这种行为也被称为查找的被动形式 =》类似于需要一个鸡蛋买回来放入篮子中，但是我把篮子放在门口（需要标注需要的东西），然后有人就会自动的把需要的鸡蛋（内容）放入到篮子中，这样便可以用这个鸡蛋了（内容）。

