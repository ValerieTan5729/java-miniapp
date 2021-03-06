package com.github.valerie.wx.miniapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.valerie.wx.miniapp.config.webLogin.LoginFilter;
import com.github.valerie.wx.miniapp.model.User;
import com.github.valerie.wx.miniapp.service.UserService;
import com.github.valerie.wx.miniapp.utils.response.RespBean;
import com.github.valerie.wx.miniapp.config.wxLogin.WxAuthenticationSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@Configuration
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private WxAuthenticationSecurityConfig wxAuthenticationSecurityConfig;

    @Autowired
    private UserService service;

    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;

    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setUsernameParameter("phone");
        loginFilter.setPasswordParameter("password");
        // 登录时候访问的url
        loginFilter.setFilterProcessesUrl("/login");
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            // 登录成功处理器
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            User user = (User) authentication.getPrincipal();
            user.setPassword(null);
            RespBean res = RespBean.ok("登录成功", user);
            String s = new ObjectMapper().writeValueAsString(res);
            // out.write("登录成功！");
            out.write(s);
            out.flush();
            out.close();
        });
        loginFilter.setAuthenticationFailureHandler(
            (request, response, exception) -> {
                // 登录失败处理器
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                String resp = "登录失败!";
                if (exception instanceof LockedException) {
                    resp += "账户被锁定，请联系管理员!";
                } else if (exception instanceof CredentialsExpiredException) {
                    resp += "密码过期，请联系管理员!";
                } else if (exception instanceof AccountExpiredException) {
                    resp += "账户过期，请联系管理员!";
                } else if (exception instanceof DisabledException) {
                    resp += "账户被禁用，请联系管理员!";
                } else if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
                    resp += "用户名或者密码输入错误，请重新输入!";
                }
                // out.write(new ObjectMapper().writeValueAsString(respBean));
                out.write(resp);
                out.flush();
                out.close();
            }
        );
        loginFilter.setAuthenticationManager(authenticationManager());
        return loginFilter;
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽视swaggger2
        web.ignoring().antMatchers( "/v2/api-docs", "/definitions/**", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/ui",
                                    "/css/**","/js/**", "/index.html","/img/**","/fonts/**","/favicon.ico");
        // 忽略的url无法获取当前用户的登录信息
        // web.ignoring().antMatchers("/wx/user/**","/css/**","/js/**","/index.html","/img/**","/fonts/**","/favicon.ico");
        // web.ignoring().antMatchers("/*/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // http.authorizeRequests().antMatchers("/wx/user/**").anonymous();
        http.apply(wxAuthenticationSecurityConfig);
        http.addFilterAfter(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        // 开启登录配置
        http.authorizeRequests()
            // 具备admin这个角色才能访问/hello这个接口
            // .antMatchers("/hello").hasRole("admin")
            // 接口需要登录之后才能访问
            // .anyRequest().authenticated()
            .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                    object.setAccessDecisionManager(customUrlDecisionManager);
                    object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                    return object;
                }
            })
            //and 方法表示结束当前标签，上下文回到HttpSecurity，开启新一轮的配置
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler((request, response, authentication) -> {
                    // 登出成功处理器
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write("登出成功");
                    out.flush();
                    out.close();
                })
            .permitAll()
            .and()
            .httpBasic()
            .and()
            .csrf().disable()
            .exceptionHandling()
            //没有认证时，在这里处理结果，不重定向到Spring Security的登录页面
            .authenticationEntryPoint((request, response, exception) -> {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(401);
                PrintWriter out = response.getWriter();
                RespBean respBean = RespBean.error("访问失败!");
                if (exception instanceof InsufficientAuthenticationException) {
                    respBean.setMsg("登录认证失败");
                }
                out.write(new ObjectMapper().writeValueAsString(respBean));
                out.flush();
                out.close();
            });
        // http.cors();
            /*
            .and().sessionManagement()
            .maximumSessions(1) //同一账号同时登录最大用户数
            //会话信息过期策略会话信息过期策略(账号被挤下线)
            .expiredSessionStrategy(sessionInformationExpiredEvent -> {
                HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(401);
                PrintWriter out = response.getWriter();
                RespBean respBean = RespBean.error("用户在别的地方登录!");
                out.write(new ObjectMapper().writeValueAsString(respBean));
                out.flush();
                out.close();

            })*/
        //记住我功能  cookie默认保存两周
        // http.rememberMe().rememberMeParameter("");
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
