package com.lagou.rpc.consumer.config;

import com.lagou.rpc.pojo.Address;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zw_jxj
 * @ClassName NettyConfig
 * @date 2021/7/26
 * @description:
 */
@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyConfig {

    List<Address> list;
}
