package com.github.valerie.wx.miniapp.config;

import com.github.valerie.wx.miniapp.model.Menu;
import com.github.valerie.wx.miniapp.model.Role;
import com.github.valerie.wx.miniapp.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 主要是根据用户传来的请求地址，分析出请求需要的角色
 * */
@Component
@Slf4j
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        log.info("进入getAttributes方法, object:{}", object.toString());
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        log.info("requestUrl:{}", requestUrl);
        List<Menu> menus = menuService.getAllMenusWithRole();
        log.info("menu list:{}", menus.toString());
        for (Menu menu : menus) {
            log.info("menu url:{}, requestUrl:{}, match result:{}", menu.getUrl(), requestUrl, antPathMatcher.match(menu.getUrl(), requestUrl));
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                if (menu.getAuth() == 0) {
                    List<Role> roles = menu.getRoles();
                    log.info("role:{}", roles);
                    String[] str = new String[roles.size()];
                    for (int i = 0; i < roles.size(); i++) {
                        str[i] = roles.get(i).getName();
                    }
                    log.info("str:{}", str);
                    return SecurityConfig.createList(str);
                } else {
                    return SecurityConfig.createList("ROLE_ANONYMOUS");
                }
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
