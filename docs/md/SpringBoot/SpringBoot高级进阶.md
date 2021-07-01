------



# SpringBoot高级进阶

## 1 数据源配置方式

### 1.1 SpringBoot提供的数据库连接池

- HikariCP（SpringBoot2.x版本默认使用）

- Commons DBCP2

- Tomcat JDBC Connection Pool

## 2 MyBatis自动配置源码剖析

### 2.1 MybatisAutoConfiguration（自动配置类）

- 类中有个MybatisProperties类，该类对应的是mybatis的配置文件

- 类中有个sqlSessionFactory方法，作用是创建SqlSessionFactory类、Configuration类（mybatis最主要的类，保存着与mybatis相关的东西）

- ③SqlSessionTemplate，作用是与mapperProxy代理类有关