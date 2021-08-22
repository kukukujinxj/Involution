package com.lagou.edu.controller;

import com.lagou.edu.pojo.Token;
import com.lagou.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/page/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/page/register")
    public String registerPage() {
        return "register";
    }

    @RequestMapping("/page/welcome")
    public String welcomePage() {
        return "welcome";
    }

    @RequestMapping("/welcome")
    public void welcome(HttpServletResponse response, String email, String password) throws IOException {
        Token token = userService.checkEmail(email);
        boolean b = false;
        if (token != null) {
            b = true;
            Cookie cookie = new Cookie("token", token.getToken());
            response.addCookie(cookie);
        }
        response.getWriter().write(String.valueOf(b));
    }

    @RequestMapping("/register")
    public void register(HttpServletResponse response, String email, String password, String code) throws IOException {
        Integer registered = userService.registered(email, password, code);
        response.getWriter().write(String.valueOf(registered));
    }

    @RequestMapping("/token")
    public void token(HttpServletResponse response, String token) throws IOException {
        String emailByToken = userService.getEmailByToken(token);
        response.getWriter().write(emailByToken);
    }
}
