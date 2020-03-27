package com.github.valerie.wx.miniapp.service;

import com.github.valerie.wx.miniapp.model.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuServiceTest {

    @Autowired
    private MenuService service;

    @Test
    public void getAllMenusByParentId() {
        List<Menu> res = this.service.getAllMenusByParentId(0);
        log.info("res:{}", res);
    }

    @Test
    public void getAllMenusWithRole() {
        List<Menu> res = this.service.getAllMenusWithRole();
        log.info("res:{}", res);
    }

    @Test
    public void getMenuByUserID() {
        Menu res = this.service.getMenuByUserID((long) 1);
        log.info("res:{}", res);
    }

    @Test
    public void selectById() {
        Menu res = this.service.selectById((long) 5);
        log.info("res:{}", res);
    }

    @Test
    public void selectAllPaging() {
        List<Menu> res = this.service.selectAllPaging(0, 10);
        log.info("res:{}", res);
    }

    @Test
    public void selectAll() {
        Menu menu = new Menu();
        menu.setParentId((long) 1);
        List<Menu> res = this.service.selectAll(menu);
        log.info("res:{}", res);
    }

    @Test
    public void select() {
        Map<String, Object> param = new HashMap<>();
        param.put("page", 0);
        param.put("limit", 10);
        param.put("parentId", 1);
        List<Menu> res = this.service.select(param);
        log.info("res:{}", res);
    }

    @Test
    public void add() {
        Menu menu = new Menu();
        menu.setId((long) 8);
        menu.setName("测试菜单");
        menu.setUrl("/test/**");
        int res = this.service.add(menu);
        log.info("res:{}, insert id:{}", res, menu.getId());
    }

    @Test
    public void update() {
        Menu menu = new Menu();
        menu.setId((long) 8);
        menu.setName("测试菜单2");
        menu.setUrl("/test/**");
        int res = this.service.update(menu);
        log.info("res:{}", res);
    }

    @Test
    public void deleteById() {
        int res = this.service.deleteById((long) 8);
        log.info("res:{}", res);
    }
}
