------



# Dubbo架构设计和源码剖析

## 1 Dubbo架构

### 1.1 Dubbo架构概述

- Apache Dubbo是一款高性能的Java RPC框架

- 六大核心能力：面向接口代理的高性能RPC调用，智能容错和负载均衡，服务自动注册和发现，高度可扩展能力，运行期流量调度，可视化的服务治理与运维

### 1.2 Dubbo处理流程

- ![](	https://dubbo.apache.org/imgs/architecture.png)

- | 节点 | 角色名称 |
  | :-----| :---- |
  | Provider | 暴露服务的服务提供方 |
  | Consumer | 调用远程服务的服务消费方 |
  | Registry | 服务注册与发现的注册中心 |
  | Monitor | 统计服务的调用次数和调用时间的监控中心 |
  | Container | 服务运行容器 负责启动 加载 运行服务提供者 |

- 调用流程:

    - 服务提供者在服务容器启动时 向注册中心注册自己提供的服务

    - 服务消费者在启动时 向注册中心订阅自己所需的服务

    - 注册中心返回服务提供者地址列表给消费者 如果有变更注册中心会基于长连接推送变更数据给消费者

    - 服务消费者从提供者地址列表中基于软负载均衡算法选一台提供者进行调用，如果调用失败则重新选择一台

    - 服务提供者和消费者在内存中的调用次数和调用时间定时每分钟发送给监控中心

## 2 Dubbo实战

### 2.1 配置方式

- 注解: 基于注解可以快速的将程序配置，无需多余的配置信息，包含提供者和消费者。但是这种方式有一个弊端，有些时候配置信息并不是特别好找，无法快速定位

- XML: 一般这种方式会和Spring做结合，相关的Service和Reference均使用Spring集成后的。通过这样的方式可以很方便的通过几个文件进行管理整个集群配置。可以快速定位也可以快速更改

- 基于代码方式: 基于代码方式的对上述配置进行配置。这个使用的比较少，这种方式更适用于公司对其框架与Dubbo做深度集成时才会使用

### 2.2 管理控制台dubbo-admin

- 主要作用：服务管理、路由规则、动态配置、服务降级、访问控制、权重调整、负载均衡等管理功能

### 2.3 配置项说明

- dubbo:application

    - 对应 org.apache.dubbo.config.ApplicationConfig, 代表当前应用的信息
    
    - name: 当前应用程序的名称，在dubbo-admin中我们也可以看到，这个代表这个应用名称。我们在真正时是时也会根据这个参数来进行聚合应用请求
    
    - owner: 当前应用程序的负责人，可以通过这个负责人找到其相关的应用列表，用于快速定位到责任人
      
    - qosEnable : 是否启动QoS 默认true
    
    - qosPort : 启动QoS绑定的端口 默认22222
    
    - qosAcceptForeignIp: 是否允许远程访问 默认是false

- dubbo:registry

    - org.apache.dubbo.config.RegistryConfig, 代表该模块所使用的注册中心。一个模块中的服务可以将其注册到多个注册中心上，也可以注册到一个上。后面再service和reference也会引入这个注册中心
    
    - id : 当当前服务中provider或者consumer中存在多个注册中心时，则使用需要增加该配置。在一些公司，会通过业务线的不同选择不同的注册中心，所以一般都会配置该值
    
    - address : 当前注册中心的访问地址
    
    - protocol : 当前注册中心所使用的协议是什么。也可以直接在address 中写入，比如使用zookeeper，就可以写成zookeeper://xx.xx.xx.xx:2181
    
    - timeout : 当与注册中心不再同一个机房时，大多会把该参数延长

- dubbo:protocol

    - org.apache.dubbo.config.ProtocolConfig, 指定服务在进行数据传输所使用的协议
    
    - id : 在大公司，可能因为各个部门技术栈不同，所以可能会选择使用不同的协议进行交互。这里在多个协议使用时，需要指定
    
    - name : 指定协议名称。默认使用dubbo 

- dubbo:service

    - org.apache.dubbo.config.ServiceConfig, 用于指定当前需要对外暴露的服务信息
    
    - interface : 指定当前需要进行对外暴露的接口是什么
    
    - ref : 具体实现对象的引用，一般我们在生产级别都是使用Spring去进行Bean托管的，所以这里面一般也指的是Spring中的BeanId
    
    - version : 对外暴露的版本号。不同的版本号，消费者在消费的时候只会根据固定的版本号进行消费

- dubbo:reference

    - org.apache.dubbo.config.ReferenceConfig, 消费者的配置
    
    - id : 指定该Bean在注册到Spring中的id
    
    - interface: 服务接口名
    
    - version : 指定当前服务版本，与服务提供者的版本一致
    
    - registry : 指定所具体使用的注册中心地址。这里面也就是使用上面在dubbo:registry 中所声明的id

- dubbo:method

    - org.apache.dubbo.config.MethodConfig, 用于在制定的dubbo:service 或者dubbo:reference 中的更具体一个层级，指定具体方法级别在进行RPC操作时候的配置，可以理解为对这上面层级中的配置针对于具体方法的特殊处理
    
    - name : 指定方法名称，用于对这个方法名称的RPC调用进行特殊配置
    
    - async: 是否异步 默认false

- dubbo:service和dubbo:reference详解

    - mock: 用于在方法调用出现错误时，当做服务降级来统一对外返回结果
    
    - timeout: 用于指定当前方法或者接口中所有方法的超时时间
    
    - check: 用于在启动时，检查生产者是否有该服务。一般都会将这个值设置为false，不让其进行检查。因为如果出现模块之间循环引用的话，那么则可能会出现相互依赖，都进行check的话，那么这两个服务永远也启动不起来
    
    - retries: 用于指定当前服务在执行时出现错误或者超时时的重试机制
    
        - 注意提供者是否有幂等，否则可能出现数据一致性问题
    
        - 注意提供者是否有类似缓存机制，如出现大面积错误时，可能因为不停重试导致雪崩
    
    - executes: 用于在提供者做配置，来确保最大的并行度
    
        - 可能导致集群功能无法充分利用或者堵塞
    
        - 但是也可以启动部分对应用的保护功能
    
        - 可以不做配置，结合后面的熔断限流使用