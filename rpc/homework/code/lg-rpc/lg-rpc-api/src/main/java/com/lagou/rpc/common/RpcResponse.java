package com.lagou.rpc.common;

import com.lagou.rpc.pojo.Address;
import lombok.Data;

/**
 * 封装的响应对象
 */
@Data
public class RpcResponse {

    /**
     * 响应ID
     */
    private String requestId;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 返回的结果
     */
    private Object result;

    /**
     * 服务地址
     */
    private Address address;

}