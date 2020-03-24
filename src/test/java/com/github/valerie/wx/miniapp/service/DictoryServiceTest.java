package com.github.valerie.wx.miniapp.service;

import com.github.valerie.wx.miniapp.model.Dictory;
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
public class DictoryServiceTest {

    @Autowired
    private DictoryService service;

    @Test
    public void getAllDictory() {
        List<Dictory> res = this.service.getAllDictory();
        log.info("res:{}", res);
    }

    @Test
    public void getAllDictoryByParentId() {
        List<Dictory> res = this.service.getAllDictoryByParentId((long) 2);
        log.info("res:{}", res);
    }

    @Test
    public void selectById() {
        Dictory res = this.service.selectById((long) 2);
        log.info("res:{}", res);
    }

    @Test
    public void selectAllPaging() {
        List<Dictory> res = this.service.selectAllPaging(0, 10);
        log.info("res:{}", res);
    }

    @Test
    public void selectAll() {
        Dictory dictory = new Dictory();
        dictory.setName("打卡地点");
        List<Dictory> res = this.service.selectAll(dictory);
        log.info("res:{}", res);
    }

    @Test
    public void select() {
        Map<String, Object> param = new HashMap<>();
        param.put("page", 0);
        param.put("limit", 10);
        param.put("name", "打卡地点");
        List<Dictory> res = this.service.select(param);
        log.info("res:{}", res);
    }

    @Test
    public void count() {
        Map<String, Object> param = new HashMap<>();
        param.put("page", 0);
        param.put("limit", 10);
        param.put("name", "打卡地点");
        Integer res = this.service.count(param);
        log.info("res:{}", res);
    }

    @Test
    public void add() {
        Dictory dictory = new Dictory();
        dictory.setName("add test");
        dictory.setParentId((long) 0);
        int res = this.service.add(dictory);
        log.info("res:{}", res);
    }

    @Test
    public void update() {
        Dictory dictory = new Dictory();
        dictory.setId((long) 999);
        dictory.setName("update test");
        int res = this.service.update(dictory);
        log.info("res:{}", res);
    }

    @Test
    public void deleteById() {
        int res = this.service.deleteById((long) 999);
        log.info("res:{}", res);
    }
}
