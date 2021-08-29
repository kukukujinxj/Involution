package com.lagou.edu.service.impl;

import com.lagou.edu.service.EmailService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@org.springframework.stereotype.Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmailAddress;

    @Override
    public String sendEmail(String emailAddress, String code) {
        try {
            final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件主题
            simpleMailMessage.setSubject("收到来自【lagou】发送的验证码");
            //邮件内容
            simpleMailMessage.setText("验证码：" + code);
            //邮件发给谁
            simpleMailMessage.setTo(emailAddress);
            //邮件来自于谁
            simpleMailMessage.setFrom(fromEmailAddress);
            javaMailSender.send(simpleMailMessage);
            return "发送成功";
        } catch (MailException e) {
            e.printStackTrace();
            return "失败成功";
        }
    }
}
