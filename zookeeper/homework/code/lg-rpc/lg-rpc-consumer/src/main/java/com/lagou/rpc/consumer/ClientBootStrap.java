package com.lagou.rpc.consumer;

import com.lagou.rpc.zookeeper.ConnectionZookeeper;

/**
 * 测试类
 */
public class ClientBootStrap {
    public static void main(String[] args) {
//        IUserService userService = (IUserService) RpcClientProxy.createProxy(IUserService.class);
//        User user = userService.getById(1);
//        System.out.println(user);
        ConnectionZookeeper connectionZookeeper = new ConnectionZookeeper();
        connectionZookeeper.connect("192.168.33.10:2181", false);
        System.out.println("1111");
    }
}
