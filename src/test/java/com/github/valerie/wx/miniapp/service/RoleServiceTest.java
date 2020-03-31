package com.github.valerie.wx.miniapp.service;

import com.github.valerie.wx.miniapp.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class RoleServiceTest {

    @Autowired
    private RoleService service;

    @Test
    public void getUserRolesById() {
        List<Role> roles = this.service.getUserRolesById((long) 1);
        log.info("res: {}", roles);
    }

    @Test
    public void selectById() {
        Role res = this.service.selectById((long) 1);
        log.info("res:{}", res);
    }

    @Test
    public void selectAllPaging() {
        List<Role> res = this.service.selectAllPaging(0, 10);
        log.info("res:{}", res);
    }

    @Test
    public void selectAll() {
        Role role = new Role();
        role.setName("admin");
        List<Role> res = this.service.selectAll(role);
        log.info("res:{}", res);
    }

    @Test
    public void select() {
        Map<String, Object> param = new HashMap<>();
        param.put("page", 0);
        param.put("limit", 10);
        param.put("name", "admin");
        List<Role> res = this.service.select(param);
        log.info("res:{}", res);
    }

    @Test
    public void add() {
        Role role = new Role();
        role.setId((long) 3);
        role.setName("manager");
        role.setNamezh("部门经理");
        int res = this.service.add(role);
        log.info("res:{}, insert id:{}", res, role.getId());
    }

    @Test
    public void update() {
        Role role = new Role();
        role.setId((long) 3);
        role.setNamezh("项目经理");
        int res = this.service.update(role);
        log.info("res:{}", res);
    }

    @Test
    public void deleteById() {
        int res = this.service.deleteById((long) 3);
        log.info("res:{}", res);
    }
}
