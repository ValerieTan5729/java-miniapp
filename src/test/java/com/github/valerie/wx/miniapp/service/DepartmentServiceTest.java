package com.github.valerie.wx.miniapp.service;

import com.github.valerie.wx.miniapp.model.Department;
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
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService service;

    @Test
    public void getAllDepartments() {
        List<Department> res = this.service.getAllDepartments();
        log.info("res:{}", res);
    }

    @Test
    public void getAllDepartmentsByParentId() {
        List<Department> res = this.service.getAllDepartmentsByParentId(2);
        log.info("res:{}", res);
    }

    @Test
    public void selectById() {
        Department res = this.service.selectById(4);
        log.info("res:{}", res);
    }

    @Test
    public void selectAllPaging() {
        List<Department> res = this.service.selectAllPaging(0, 10);
        log.info("res:{}", res);
    }

    @Test
    public void selectAll() {
        Department department = new Department();
        department.setParentId(2);
        List<Department> res = this.service.selectAll(department);
        log.info("res:{}", res);
    }

    @Test
    public void select() {
        Map<String, Object> param = new HashMap<>();
        param.put("page", 0);
        param.put("limit", 10);
        param.put("parentId", 2);
        List<Department> res = this.service.select(param);
        log.info("res:{}", res);
    }

    @Test
    public void add() {
        Department dep = new Department();
        dep.setId(10);
        dep.setName("test valerie");
        int res = this.service.add(dep);
        log.info("res:{}", res);
    }

    @Test
    public void update() {
        Department dep = new Department();
        dep.setId(10);
        dep.setName("test haha");
        int res = this.service.update(dep);
        log.info("res:{}", res);
    }

    @Test
    public void deleteById() {
        int res = this.service.deleteById(10);
        log.info("res:{}", res);
    }

    @Test
    public void deleteDepById() {
        Department dep = new Department();
        dep.setId(1);
        this.service.deleteDepById(dep);
        log.info("res:{}", dep);
        if (dep.getResult() == -2) {
            log.info("该部门下有子部门，删除失败");
        } else if (dep.getResult() == -1) {
            log.info("该部门下有员工，删除失败");
        } else if (dep.getResult() == 1) {
            log.info("删除成功");
        }
    }
}
