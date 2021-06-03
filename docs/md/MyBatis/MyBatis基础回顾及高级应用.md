------



# MyBatis基础回顾及高级应用

## 1 缓存

### 1.1 一级缓存

- 一级缓存是SqlSession级别的缓存，不同的SqlSession之间缓存区域互不影响。存储结构为HashMap。

    - 执行过程：首先在一级缓存中查询，有则返回，没有则查询数据库并将结果存在一级缓存中。做增删改操作，并进行事务提交会清空一级缓存。clearCache()可以手动清空缓存。
    
    - HashMap
    
        - Key：CacheKey(StatementId、Params、BoundSql、RowBounds)
    
              StatementId：namespace + 方法名
              Params：当前参数
              BoundSql：封装当前执行SQL
              RowBounds： 分页对象
    
        - Value：当前查询结果

### 1.2 二级缓存

- 二级缓存是Mapper级别的缓存，多个SqlSession操作同一个Mapper下的SQL语句，多个SqlSession可以共用二级缓存，二级缓存是跨SqlSession的。如果两个Mapper的namespace相同，那么这两个Mapper中执行的SQL查询的数据也将存储在相同的二级缓存中。

- 二级缓存缓存数据，会重新生成数据对象缓存，多次查询数据相等，但是对象地址不相等。

- 开启二级缓存需要实现Serializable接口，为了将缓存数据取出执行反序列化操作，因为二级缓存数据存储介质多种多样，不一定只存在内存中，有可能存在硬盘中，如果再取缓存时，就需要反序列化了。

- 二级缓存默认的HashMap存储结构没办法处理分布式缓存，可以使用RedisCache实现分布式缓存（Redis数据结构为Hash）。

## 2 插件

### 2.1 介绍

- 为四大组件（Executor、StatementHandle、ParameterHandle、ResultHandle）提供插件扩展机制。MyBatis借助底层动态代理，支持使用插件对四大核心对象进行拦截，以增强核心功能。

     - 执行器Executor (update、query、commit、rollback等方法)
        
     - SQL语法构建器StatementHandler (prepare、parameterize、batch、updates query等方法)
        
     - 参数处理器ParameterHandler (getParameterObject、setParameters方法)
        
     - 结果集处理器ResultSetHandler (handleResultSets、handleOutputParameters等方法)

### 2.2 自定义插件

- 实现Interceptor接口

    - intercept：拦截方法。只要被拦截的目标对象的目标方法被执行时，每次都会执行。
    
    - plugin：把当前拦截器生成代理存到拦截器链中。
    
    - setProperties：获取配置文件参数。

### 2.3 第三方插件
    
- PageHelper：实现Interceptor接口，对Executor对象的query方法进行拦截。

- 通用Mapper：实现Interceptor接口，不需要编写SQL，不需要在DAO中增加方法，只要写好实体类，就能支持相应的增删改查方法。对Executor对象的query、update方法进行拦截