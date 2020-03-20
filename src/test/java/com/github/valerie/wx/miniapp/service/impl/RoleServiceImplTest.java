package com.github.valerie.wx.miniapp.service.impl;

import com.github.valerie.wx.miniapp.model.Role;
import com.github.valerie.wx.miniapp.service.RoleService;
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
public class RoleServiceImplTest {

    @Autowired
    private RoleService service;

    @Test
    public void getUserRolesById() {
        List<Role> roles = this.service.getUserRolesById((long) 1);
        log.info("res: {}", roles);
    }

    @Test
    public void selectById() {
        Role role = this.service.selectById(1);

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
}
