package com.lagou.edu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "lagou-service-code", path = "/api/code")
public interface CodeServiceFeignClient {
    @RequestMapping("/validate")
    public String inspectionCode(String code);
}
