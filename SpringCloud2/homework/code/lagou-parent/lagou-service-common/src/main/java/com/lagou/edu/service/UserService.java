package com.lagou.edu.service;

import com.lagou.edu.pojo.Token;

import java.text.ParseException;

public interface UserService {
    public Integer registered(String email, String password, String code) throws ParseException;

    public Token checkEmail(String email);

    public boolean saveToken(String email, String password);

    public String getEmailByToken(String token);
}
