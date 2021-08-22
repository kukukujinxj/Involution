package com.lagou.edu.service;

import com.lagou.edu.pojo.Token;

public interface UserService {
    public Integer registered(String email, String password, String code);

    public Token checkEmail(String email);

    public boolean saveToken(String email, String password);

    public String getEmailByToken(String token);
}
