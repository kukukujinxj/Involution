package com.lagou.bean;

import com.lagou.service.HelloService;
import com.lagou.service.MethodService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class ComsumerComponet {
    @DubboReference(timeout = 10000)
    private HelloService helloService;

    @DubboReference(timeout = 10000)
    private MethodService methodService;

    public String sayHello(String name) {
        return helloService.sayHello(name);
    }

    public String methodA() {
        return methodService.methodA();
    }

    public String methodB() {
        return methodService.methodB();
    }

    public String methodC() {
        return methodService.methodC();
    }
}
