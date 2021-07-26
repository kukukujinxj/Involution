package com.lagou.rpc.consumer.lb;

import com.lagou.rpc.consumer.config.NettyConfig;
import com.lagou.rpc.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zw_jxj
 * @ClassName RoundRobin
 * @date 2021/7/26
 * @description:
 */
@Component
public class RoundRobin {

    @Autowired
    NettyConfig nettyConfig;

    private int COUNT = 0;

    public synchronized Address getAddress() {
        List<Address> list = nettyConfig.getList();
        int carry = this.COUNT % list.size();
        this.COUNT++;
        return list.get(carry);
    }

}
