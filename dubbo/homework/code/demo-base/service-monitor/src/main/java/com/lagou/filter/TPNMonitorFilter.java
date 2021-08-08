package com.lagou.filter;


import com.lagou.pojo.MethodInfo;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Activate(group = {CommonConstants.CONSUMER})
public class TPNMonitorFilter implements Filter, Runnable {

    Map<String, List<MethodInfo>> map = new ConcurrentHashMap<>();

    public TPNMonitorFilter() {
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this, 5, 5, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        for (Map.Entry<String, List<MethodInfo>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "的TP90：" + getTP(entry.getValue(), 0.9) + "毫秒，TP99：" + getTP(entry.getValue(), 0.99) + "毫秒");
        }
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long startTime = System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        long endTime = System.currentTimeMillis();
        long exeTime = endTime - startTime;
        String methodName = invocation.getMethodName();
        List<MethodInfo> methodInfoList = map.computeIfAbsent(methodName, k -> new ArrayList<>());
        methodInfoList.add(new MethodInfo(methodName, exeTime, endTime));
        return result;
    }

    private long getTP(List<MethodInfo> methodInfos, double rate) {
        List<MethodInfo> list = new ArrayList<>();
        long endTime = System.currentTimeMillis();
        long startTime = System.currentTimeMillis() - 60000;

        for (MethodInfo methodInfo : methodInfos) {
            if (methodInfo.getEndTime() >= startTime && methodInfo.getEndTime() <= endTime) {
                list.add(methodInfo);
            }
        }

        list.sort(Comparator.comparingLong(MethodInfo::getExeTime));

        int index = (int) (list.size() * rate);
        return list.get(index).getExeTime();
    }
}
