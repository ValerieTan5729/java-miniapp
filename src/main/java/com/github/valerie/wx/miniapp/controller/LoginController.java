package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.model.User;
import com.github.valerie.wx.miniapp.service.DepartmentService;
import com.github.valerie.wx.miniapp.service.DictoryService;
import com.github.valerie.wx.miniapp.service.UserService;
import com.github.valerie.wx.miniapp.utils.response.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    private DictoryService dictoryService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/doLogin")
    public RespBean login(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        currentUser.setDepName(this.departmentService.selectById(currentUser.getDepId()).getName());
        currentUser.setDutyLevelName(this.dictoryService.selectById(Long.valueOf(currentUser.getDutyLevelId())).getName());
        return RespBean.ok("已经登录后端服务器", currentUser);
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
