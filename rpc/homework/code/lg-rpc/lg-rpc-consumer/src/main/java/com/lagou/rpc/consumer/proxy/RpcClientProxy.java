package com.lagou.rpc.consumer.proxy;

import com.alibaba.fastjson.JSON;
import com.lagou.rpc.common.RpcRequest;
import com.lagou.rpc.common.RpcResponse;
import com.lagou.rpc.consumer.client.RpcClient;
import com.lagou.rpc.consumer.lb.RoundRobin;
import com.lagou.rpc.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 客户端代理类-创建代理对象
 * 1.封装request请求对象
 * 2.创建RpcClient对象
 * 3.发送消息
 * 4.返回结果
 */
@Component
public class RpcClientProxy {

    @Autowired
    RoundRobin roundRobin;

    public Object createProxy(Class serviceClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //1.封装request请求对象
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId(UUID.randomUUID().toString());
                        rpcRequest.setClassName(method.getDeclaringClass().getName());
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setParameterTypes(method.getParameterTypes());
                        rpcRequest.setParameters(args);
                        //轮询
                        Address address = roundRobin.getAddress();
                        //2.创建RpcClient对象
//                        RpcClient rpcClient = new RpcClient("127.0.0.1", 8899);
                        RpcClient rpcClient = new RpcClient(address.getHost(), address.getPort());
                        try {
                            //3.发送消息
                            Object responseMsg = rpcClient.send(JSON.toJSONString(rpcRequest));
                            RpcResponse rpcResponse = JSON.parseObject(responseMsg.toString(), RpcResponse.class);
                            if (rpcResponse.getError() != null) {
                                throw new RuntimeException(rpcResponse.getError());
                            }
                            //4.返回结果
                            Object result = rpcResponse.getResult();
                            Address serverAddress = rpcResponse.getAddress();
                            Field addressField = ReflectionUtils.findField(method.getReturnType(), "address");
                            if (addressField != null) {
                                addressField.setAccessible(true);
                            }
                            Object returnResult = JSON.parseObject(result.toString(), method.getReturnType());
                            if (addressField != null) {
                                ReflectionUtils.setField(addressField, returnResult, serverAddress);
                            }
                            return returnResult;
                        } catch (Exception e) {
                            throw e;
                        } finally {
                            rpcClient.close();
                        }

                    }
                });
    }
}
