package com.github.valerie.wx.miniapp.service;

import com.github.valerie.wx.miniapp.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void selectById() {
        User user = this.service.selectById((long) 1);
        log.info("res: {}", user);
    }

    @Test
    public void selectIdWithRole() {
        User user = this.service.selectIdWithRole((long) 1);
        log.info("res: {}", user);
    }

    @Test
    public void selectAllPaging() {
        List<User> users = this.service.selectAllPaging(0, 10);
        log.info("res: {}", users);
    }

    @Test
    public void selectAll() {
        User user = new User();
        user.setPhone("15692009328");
        List<User> users = this.service.selectAll(user);
        log.info("res: {}", users);
    }

    @Test
    public void select() {
        Map<String, Object> param = new HashMap<>();
        param.put("page", 0);
        param.put("limit", 0);
        param.put("name", "valerie");
        List<User> users = this.service.select(param);
        log.info("res: {}", users);
    }

    @Test
    public void add() {
        User user = new User();
        user.setName("test");
        user.setPassword(new BCryptPasswordEncoder().encode("0824,Love"));
        user.setPhone("13531646616");
        int res = this.service.add(user);
        log.info("res : {}", res);
        /*
        User u1 = this.service.selectById(user.getId());
        log.info("password res : {}", new BCryptPasswordEncoder().matches("0824,Love", u1.getPassword()));
        */
    }

    @Test
    public void update() {
        User user = new User();
        user.setId((long) 3);
        user.setName("test");
        user.setPassword(new BCryptPasswordEncoder().encode("0824,Love"));
        user.setPhone("13531646616");
        int res = this.service.update(user);
        log.info("res : {}", res);
    }

    @Test
    public void deleteById() {
        int res = this.service.deleteById((long) 4);
        log.info("res : {}", res);
    }

    @Test
    public void loadUserByUsername() {
        User user = ((User) this.service.loadUserByUsername("15692009328"));
        log.info("user info is {}", user.toString());
    }

    @Test
    public void updateUserRole() {
        List<Long> roleList = new ArrayList<>();
        roleList.add((long) 1);
        roleList.add((long) 2);
        boolean res = this.service.updateUserRole((long) 1, roleList);
        log.info("res is {}", res);
    }
}
