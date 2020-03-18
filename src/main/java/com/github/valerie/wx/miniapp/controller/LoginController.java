package com.github.valerie.wx.miniapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("LoginController")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "尚未登录，请登录!";
    }

}
