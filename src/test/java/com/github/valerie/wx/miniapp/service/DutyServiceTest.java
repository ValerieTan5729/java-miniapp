package com.github.valerie.wx.miniapp.service;

import com.github.valerie.wx.miniapp.model.Duty;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DutyServiceTest {

    @Autowired
    private DutyService service;

    @Test
    public void selectById() {
        Duty res = this.service.selectById((long) 1);
        log.info("res:{}", res);
    }

    @Test
    public void selectAllPaging() {
        List<Duty> res = this.service.selectAllPaging(0, 10);
        log.info("res:{}", res);
    }

    @Test
    public void selectAll() {
        Duty duty = new Duty();
        duty.setName("测试");
        List<Duty> res = this.service.selectAll(duty);
        log.info("res:{}", res);
    }

    @Test
    public void select() {
        Map<String, Object> param = new HashMap<>();
        param.put("page", 0);
        param.put("limit", 10);
        param.put("beginDate", "2020-02-01");
        List<Duty> res = this.service.select(param);
        log.info("res:{}", res);
    }

    @Test
    public void count() {
        Map<String, Object> param = new HashMap<>();
        param.put("page", 0);
        param.put("limit", 10);
        param.put("beginDate", "2020-02-01");
        long res = this.service.count(param);
        log.info("res:{}", res);
    }

    @Test
    public void add() {
        Duty duty = new Duty();
        duty.setName("add test");
        int res = this.service.add(duty);
        log.info("res:{}", res);
    }

    @Test
    public void update() {
        Duty duty = new Duty();
        duty.setId((long) 1);
        duty.setName("update test");
        int res = this.service.update(duty);
        log.info("res:{}", res);
    }

    @Test
    public void deleteById() {
        int res = this.service.deleteById((long) 2);
        log.info("res:{}", res);
    }
}
