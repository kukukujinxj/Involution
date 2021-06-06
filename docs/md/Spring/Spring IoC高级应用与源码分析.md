------



# Spring IoC高级应用与源码分析

## 1 Spring IoC基础知识

### 1.1 beans.xml与BeanFactory

- beans.xml：定义需要实例化对象的全限定类名以及类之间的依赖关系描述

- BeanFactory：通过反射来实例化对象并维护对象之间的依赖关系

### 1.2 Spring框架的IoC实现

- 纯xml：bean信息全部配置在xml中

- xml+注解：部分bean使用xml配置，部分bean使用注解配置

- 纯注解：bean信息全部使用注解配置

### 1.3 不同实现下的IoC容器启动方式

- 纯xml、xml+注解

    - JavaSE：
        
        - ```ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");```
            
        - ```ApplicationContext applicationContext = new FileSystemXmlApplicationContext("c:/beans.xml");```

    - JavaWeb：
    
        - ContextLoaderListener(监听器加载xml)
        
- 纯注解

    - JavaSE：
    
        - ```ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConifg.class);```
        
    - JavaWeb:
    
        - ContextLoaderListener(监听器加载注解配置类)

### 1.4 BeanFactory和ApplicationContext的区别

- BeanFactory：Spring容器顶层接口，定义基础基础功能和基础规范

- ApplicationContext：BeanFactory的子接口，具备BeanFactory全部功能，并提供额外功能，如国际化支持和资源访问（xml、Java配置类）等

    - ClassPathXmlApplicationContext
    
    - FileSystemXmlApplicationContext
    
    - AnnotationConfigApplicationContext

### 1.5 Bean的创建方式

- 使用无参构造器（推荐）

- 静态方法

- 实例化方法

### 1.6 SpringBean生命周期

- Spring容器启动时，读取配置文件，将每个Bean解析为BeanDefinition结构，封装Bean的配置信息

- 根据配置情况调用Bean构造方法或工厂方法实例化Bean

- 利用依赖注入完成Bean中属性的配置注入

- 如果Bean实现了BeanNameAware接口，则调用setBeanName方法获取Bean的Id

- 如果Bean实现了BeanFactoryAware接口，则调用setBeanFactory方法获取BeanFactory实例的引用

- 如果Bean实现了ApplicationContextAware接口，则调用setApplicationContext方法获取当前ApplicationContext实例的引用

- 如果BeanPostProcessor和Bean关联，则调用该接口的预处理方法setProcessBeforeInitialization对Bean加功操作，Spring在此处获取代理对象
        
- @PostConstruct
       
- 如果Bean实现了InitializingBean接口，则调用afterPropertiesSet方法

- 如果在配置文件中通过init-method属性指定初始化方法，则调用该方法

- 如果BeanPostProcessor和Bean关联，则调用该接口的预处理方法setProcessAfterInitialization，此时Bean已经可以被应用系统使用了

- 如果指定Bean的作用范围是singleton，则触发Spring对该Bean生命周期的管理；如果作用范围为prototype，则由调用者管理

- @PreDestroy

- 如果Bean实现了DisposableBean接口，则调用destroy方法销毁Bean

- 如果在配置文件中通过destroy-method属性指定销毁方法，则调用该方法
