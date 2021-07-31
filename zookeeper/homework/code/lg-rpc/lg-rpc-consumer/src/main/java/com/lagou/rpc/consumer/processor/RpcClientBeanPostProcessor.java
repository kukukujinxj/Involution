package com.lagou.rpc.consumer.processor;

import com.lagou.rpc.consumer.anno.RpcConsumerService;
import com.lagou.rpc.consumer.proxy.RpcClientProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zw_jxj
 * @ClassName RpcClientBeanPostProcessor
 * @date 2021/7/26
 * @description:
 */
@Component
public class RpcClientBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    RpcClientProxy rpcClientProxy;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        if (aClass.isAnnotationPresent(RestController.class) || aClass.isAnnotationPresent(Controller.class)) {
            Field[] fields = aClass.getDeclaredFields();
            List<Field> fieldList = Arrays.stream(fields).filter(field -> field.isAnnotationPresent(RpcConsumerService.class)).collect(Collectors.toList());
            fieldList.forEach(field -> {
                Class<?> type = field.getType();
                Object proxy = rpcClientProxy.createProxy(type);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, proxy);
            });
        }
        return bean;
    }
}
