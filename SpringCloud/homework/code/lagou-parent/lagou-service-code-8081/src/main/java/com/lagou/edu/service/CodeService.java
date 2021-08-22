package com.lagou.edu.service;

import java.text.ParseException;

public interface CodeService {
    public String toGenerateVerificationCode();

    public String doSendEmail(String code, String EmailAddress);

    public String inspectionCode(String code) throws ParseException;
}
