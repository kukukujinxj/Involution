package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LagouCloudGatewayApp9002 {
    public static void main(String[] args) {
        SpringApplication.run(LagouCloudGatewayApp9002.class, args);
    }
}
