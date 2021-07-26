package com.lagou.rpc.consumer.controller;

import com.lagou.rpc.api.IUserService;
import com.lagou.rpc.consumer.anno.RpcConsumerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zw_jxj
 * @ClassName UserController
 * @date 2021/7/26
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RpcConsumerService
    IUserService iUserService;

    @GetMapping("/getById")
    public Object getById(Integer id) {
        return iUserService.getById(id);
    }
}
