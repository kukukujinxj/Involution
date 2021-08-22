package com.lagou.edu.service.impl;

import com.lagou.edu.service.UserService;
import com.lagou.edu.dao.TokenDao;
import com.lagou.edu.feign.CodeServiceFeignClient;
import com.lagou.edu.pojo.Token;
import com.lagou.edu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private CodeServiceFeignClient codeServiceFeignClient;

    @Override
    public Integer registered(String email, String password, String code) {
        if (checkEmail(email) != null) {
            return 3;
        }
        int res = 0;
        String status = codeServiceFeignClient.inspectionCode(code);
        switch (status) {
            case "1":
                saveToken(email, password);
                break;
            case "2":
                res = 2;
                break;
            default:
                res = 1;
                break;
        }
        return res;
    }

    @Override
    public Token checkEmail(String email) {
        return getOptional(email).orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveToken(String email, String password) {
        String tokenStr = JwtUtil.sign(email, password);
        Optional<Token> optional = getOptional(email);
        Token token = null;
        if (optional.isPresent()) {
            token = optional.get();
            token.setToken(tokenStr);
        } else {
            token = new Token();
            token.setId((long) 0);
            token.setEmail(email);
            token.setToken(tokenStr);
        }
        tokenDao.save(token);
        return true;
    }

    @Override
    public String getEmailByToken(String token) {
        return JwtUtil.getUsername(token);
    }

    public Optional<Token> getOptional(String email) {
        Token token = new Token();
        token.setEmail(email);
        Optional<Token> one = tokenDao.findOne(Example.of(token));
        return one;
    }
}
