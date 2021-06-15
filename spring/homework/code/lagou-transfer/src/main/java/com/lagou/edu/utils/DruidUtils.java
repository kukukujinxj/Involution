package com.lagou.edu.utils;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author 应癫
 */
public class DruidUtils {

    private DruidUtils(){
    }

    private static DruidDataSource druidDataSource = new DruidDataSource();


    static {
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        druidDataSource.setUrl("jdbc:mysql://localhost:3306/bank");
        druidDataSource.setUrl("jdbc:mysql://192.168.33.10:3306/spring");
        druidDataSource.setUsername("root");
//        druidDataSource.setPassword("123456");
        druidDataSource.setPassword("root");

    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }

}
