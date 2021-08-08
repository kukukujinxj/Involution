package com.lagou.service.impl;

import com.lagou.service.MethodService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Random;

@DubboService
public class MethodServiceImpl implements MethodService {
    @Override
    public String methodA() {
        int random = new Random().nextInt(100) + 1;
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "methodA hello";
    }

    @Override
    public String methodB() {
        int random = new Random().nextInt(100) + 1;
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "methodB hello";
    }

    @Override
    public String methodC() {
        int random = new Random().nextInt(100) + 1;
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "methodC hello";
    }
}
