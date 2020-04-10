package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.utils.response.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {
    @GetMapping("/doLogin")
    public RespBean login() {
        return RespBean.error("尚未登录，请登录!");
    }

    @GetMapping("/wx/hello")
    public RespBean helloWx() {
        return RespBean.ok("你好，微信小程序的API");
    }

    @GetMapping("/wx/kjw/hello")
    public RespBean helloKJW() {
        return RespBean.ok("你好，微信小程序的API");
    }
}
