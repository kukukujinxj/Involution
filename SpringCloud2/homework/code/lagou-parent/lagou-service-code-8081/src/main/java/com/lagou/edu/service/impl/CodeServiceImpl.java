package com.lagou.edu.service.impl;

import com.lagou.edu.dao.CodeDao;
import com.lagou.edu.pojo.Code;
import com.lagou.edu.service.CodeService;
import com.lagou.edu.service.EmailService;
import com.lagou.edu.util.DateUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@org.springframework.stereotype.Service
public class CodeServiceImpl implements CodeService {

    private final String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Autowired
    private CodeDao codeDao;
    @Reference(timeout = 3000, retries = 0)
    private EmailService emailService;

    @Override
    public String toGenerateVerificationCode() {
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String doSendEmail(String codeStr, String emailAddress) {
        String status = emailService.sendEmail(emailAddress, codeStr);
        // 对数据库进行查询，看该邮箱是否存在验证码，存在覆盖，不存在则保存
        // 对验证码和邮箱进行保存
        Code code = new Code();
        code.setEmail(emailAddress);
        Optional<Code> one = codeDao.findOne(Example.of(code));
        // 覆盖
        // 保存
        code = one.orElseGet(Code::new);
        if (code.getId() == null) {
            code.setId((long) 0);
        }
        code.setCode(codeStr);
        code.setEmail(emailAddress);
        code.setCreatetime(DateUtil.getFormatNow("yyyy-MM-dd HH:mm:ss"));
        code.setExpiretime(DateUtil.formatDatetime(DateUtil.addMinute(new Date(), 1)));
        codeDao.save(code);
        return status;
    }

    @Override
    public String inspectionCode(String codeStr) throws ParseException {
        // 根据验证码查询数据库判断是否存在，并判断是否超过过期时间
        Code code = new Code();
        code.setCode(codeStr);
        Optional<Code> one = codeDao.findOne(Example.of(code));
        if (one.isPresent()) {
            code = one.get();
            boolean after = DateUtil.isBefore(new Date(), DateUtil.parse(code.getExpiretime(), "yyyy-MM-dd HH:mm:ss"));
            if (after) {
                return "1";
            } else {
                return "2";
            }
        } else {
            return "0";
        }
    }
}
