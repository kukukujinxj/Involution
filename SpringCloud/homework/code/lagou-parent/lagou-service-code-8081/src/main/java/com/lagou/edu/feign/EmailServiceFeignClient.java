package com.lagou.edu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "lagou-service-email", path = "/api/email")
public interface EmailServiceFeignClient {
    @RequestMapping("/send")
    public String sendEmail(@RequestParam("emailAddress") String emailAddress, @RequestParam("code") String code);
}
