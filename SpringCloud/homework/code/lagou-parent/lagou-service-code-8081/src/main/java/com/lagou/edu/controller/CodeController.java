package com.lagou.edu.controller;

import com.lagou.edu.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/code")
public class CodeController {
    @Autowired
    private CodeService codeService;

    @RequestMapping("/create")
    public String SendCode(String email){
        // 得到验证码
        String code = codeService.toGenerateVerificationCode();
        // 发送邮件
        String status = codeService.doSendEmail(code, email);
        return status;
    }

    @RequestMapping("/validate")
    public String inspectionCode(String code) throws ParseException {
        return codeService.inspectionCode(code);
    }
}
