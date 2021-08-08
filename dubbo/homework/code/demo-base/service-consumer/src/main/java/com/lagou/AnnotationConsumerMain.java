package com.lagou;

import com.lagou.bean.ComsumerComponet;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnnotationConsumerMain {
    public static void main(String[] args) throws Exception {
        System.out.println("-------------");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        // 获取消费者组件
        ComsumerComponet service = context.getBean(ComsumerComponet.class);
//        while(true){
//             System.in.read();
//             String  hello = service.sayHello("world");
//             System.out.println("result:"+hello);
//        }
        ExecutorService executorService = Executors.newFixedThreadPool(9);
        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                while (true) {
                    System.out.println(service.methodA());
                }
            });
            executorService.execute(() -> {
                while (true) {
                    System.out.println(service.methodB());
                }
            });
            executorService.execute(() -> {
                while (true) {
                    System.out.println(service.methodC());
                }
            });
        }
    }

    @Configuration
    @PropertySource("classpath:/dubbo-consumer.properties")
    @ComponentScan(basePackages = "com.lagou.bean")
    @EnableDubbo
    static class ConsumerConfiguration {
        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress("zookeeper://192.168.33.10:2181?timeout=10000");
            return registryConfig;
        }
    }
}
