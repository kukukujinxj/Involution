package com.lagou.rpc.consumer.lb;

import com.lagou.rpc.pojo.Address;
import com.lagou.rpc.zookeeper.ConnectionZookeeper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zw_jxj
 * @ClassName RoundRobin
 * @date 2021/7/26
 * @description:
 */
@Component
public class RoundRobin {

//    @Autowired
//    NettyConfig nettyConfig;

//    private int COUNT = 0;

    public Address getAddress(ConnectionZookeeper zookeeper) {
        List<String> childrenNode = zookeeper.getChildrenNode(zookeeper.getBASE());
        List<Address> list = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (String path : childrenNode) {
            String value = zookeeper.getNode(zookeeper.getBASE() + "/" + path).toString();
            String[] array = value.split("#");
            int responseTime = Integer.parseInt(array[0]);
            array = path.split(":");
            Address address = new Address();
            address.setHost(array[0]);
            address.setPort(Integer.parseInt(array[1]));
            list.add(address);
            if (responseTime < min) {
                min = responseTime;
            }
        }
        List<Integer> indexList = new ArrayList<>();
        int i = 0;
        for (String path : childrenNode) {
            String value = zookeeper.getNode(zookeeper.getBASE() + "/" + path).toString();
            String[] array = value.split("#");
            int responseTime = Integer.parseInt(array[0]);
            if (responseTime == min) {
                indexList.add(i);
            }
            i++;
        }

        int random = new Random().nextInt(indexList.size());
        return list.get(indexList.get(random));

//        List<Address> list = nettyConfig.getList();
//        int carry = this.COUNT % list.size();
//        this.COUNT++;
//        return list.get(carry);
    }

}
