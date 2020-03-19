package com.github.valerie.wx.miniapp.service.impl;

import com.github.valerie.wx.miniapp.model.Role;
import com.github.valerie.wx.miniapp.model.User;
import com.github.valerie.wx.miniapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService service;

    @Test
    public void selectById() {
        User user = this.service.selectById((long) 1);
        log.info("user info: {}", user.toString());
    }

    @Test
    public void selectIdWithRole() {
        User user = this.service.selectIdWithRole((long) 1);
        log.info("user info: {}", user.toString());
    }

    @Test
    public void selectAllPaging() {
    }

    @Test
    public void selectAll() {
    }

    @Test
    public void select() {
    }

    @Test
    public void add() {
    }

    @Test
    public void update() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void loadUserByUsername() {
        User user = ((User) this.service.loadUserByUsername("15692009328"));
        log.info("user info is {}", user.toString());
    }
}
