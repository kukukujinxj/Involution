package com.lagou.edu.controller;

import com.lagou.edu.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @RequestMapping("/send")
    public String sendEmail(String emailAddress, String code) {
        return emailService.sendEmail(emailAddress, code);
    }
}
