------



# SpringMVC基础回顾及高级应用

## 1 SpringMVC基础回顾

### 1.1 经典三层及MVC架构

- 经典三层

    - ![](../../images/SpringMVC/经典三层.png)

- MVC

    - M：Model模型（数据模型【pojo、vo、po】+业务模型（业务逻辑））
    
    - V：View视图（jsp、html）
    
    - C：Controller控制器（servlet）
    
- SpringMVC：应用于表现层的框架

### 1.2 SpringMVC与原生Servlet模式区别

- ![](../../images/SpringMVC/SpringMVC与Servlet区别.png)

### 1.3 SpringMVC请求处理流程

- ![](../../images/SpringMVC/SpringMVC请求处理流程.png)

### 1.4 url-pattern

- 带后缀(`*.action`、`*.do`)：拦截某种格式的请求

- `/`：不会拦截jsp，但是会拦截html等静态资源

    - 为什么拦截静态资源

        - tomcat容器有web.xml（父），项目中也有web.xml（子），是继承关系
        
        - 父web.xml中有DefaultServlet，url-pattern为`/`，处理静态资源
        
        - 子web.xml中配置`/`将会复写父web.xml中的配置，因此配置`/`会拦截静态资源
        
    - 为什么不拦截jsp
    
        - 因为父web.xml中有一个JSPServlet，这个Servlet拦截jsp，项目中没有复写该配置，因此SpringMVC不拦截jsp，jsp处理交给tomcat
        
    - 如何解决拦截静态资源
    
        - 配置`<mvc:default-servlet-handler/>`标签：添加该标签，会在SpringMVC上下文中定义一个DefaultServletHttpRequestHandler对象，该对象会对进入DispatcherServlet的url进行筛选，若是静态资源，则交由web应用服务器默认的DefaultServlet处理，否则由SpringMVC处理。缺点是只能放在webapp根目录下
        
        - 配置`<mvc:resource location="" mapping=""/>`标签：SpringMVC处理静态资源

- `/*`：拦截所有，包括jsp