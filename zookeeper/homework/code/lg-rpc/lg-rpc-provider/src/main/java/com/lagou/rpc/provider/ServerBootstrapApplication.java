package com.lagou.rpc.provider;

import com.lagou.rpc.provider.server.RpcServer;
import com.lagou.rpc.zookeeper.ConnectionZookeeper;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerBootstrapApplication implements CommandLineRunner {


    @Autowired
    RpcServer rpcServer;

    public static void main(String[] args) {
        SpringApplication.run(ServerBootstrapApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        new Thread(() -> {
////                rpcServer.startServer("127.0.0.1", 8899);
////                rpcServer.startServer("127.0.0.1", 8081);
////                rpcServer.startServer("127.0.0.1", 8082);
//            ConnectionZookeeper connectionZookeeper = new ConnectionZookeeper();
//            connectionZookeeper.connect("192.168.33.10:2181", false);
//            String path= connectionZookeeper.getBASE() + "/127.0.0.1:8081";
//            connectionZookeeper.createNode(CreateMode.EPHEMERAL, path, "0#0");
//            rpcServer.startServer("127.0.0.1", 8081);
//        }).start();
        new Thread(() -> {
            ConnectionZookeeper connectionZookeeper = new ConnectionZookeeper();
            connectionZookeeper.connect("192.168.33.10:2181", false);
            String path = connectionZookeeper.getBASE() + "/127.0.0.1:8082";
            connectionZookeeper.createNode(CreateMode.EPHEMERAL, path, "0#0");
            rpcServer.startServer("127.0.0.1", 8082);
        }).start();
    }
}
