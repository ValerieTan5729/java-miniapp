package com.github.valerie.wx.miniapp.config.wxLogin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Slf4j
@Component
public class WxAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private UserDetailsService userService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        WxAuthenticationFilter wxAuthenticationFilter = new WxAuthenticationFilter();
        wxAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        wxAuthenticationFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            // 登录成功处理器
            log.info("登录成功");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write("登录成功！");
            // out.write(s);
            out.flush();
            out.close();
        });
        wxAuthenticationFilter.setAuthenticationFailureHandler(((request, response, exception) -> {
            // 登录失败处理器
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            String resp = "登录失败!";
            out.write(resp);
            out.flush();
            out.close();
        }));

        WxAuthenticationProvider wxAuthenticationProvider = new WxAuthenticationProvider();
        wxAuthenticationProvider.setUserDetailsService(userService);

        http.authenticationProvider(wxAuthenticationProvider)
            .addFilterAfter(wxAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
