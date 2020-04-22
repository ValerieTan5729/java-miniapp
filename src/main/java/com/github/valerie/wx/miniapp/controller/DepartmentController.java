package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.model.Department;
import com.github.valerie.wx.miniapp.service.DepartmentService;
import com.github.valerie.wx.miniapp.utils.response.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("department接口")
@RestController
@RequestMapping("/basic/dep")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过ID查询单条数据")
    @ApiImplicitParam(name="id", value="ID", defaultValue="1", required=true)
    @GetMapping("/{id}")
    public RespBean selectOne(@PathVariable Integer id) {
        return RespBean.ok("获取成功", this.service.selectById(id));
    }

    /**
     * 获取所有部门信息
     * */
    @ApiOperation("获取所有部门信息")
    @GetMapping("/")
    public RespBean getAllDepartments() {
        return RespBean.ok(this.service.getAllDepartments());
    }

    /**
     * 通过Parent_ID查询下一级菜单列表
     */
    @ApiOperation("通过Parent_ID查询下一级菜单列表")
    @GetMapping("/parent/{id}")
    public RespBean selectByParentId(@PathVariable Integer id) {
        return RespBean.ok("获取成功", this.service.getAllDepartmentsByParentId(id));
    }

    /**
     * 新增部门
     * */
    @ApiOperation("新增部门")
    @PostMapping("/add")
    public RespBean add(@RequestBody Department department) {
        this.service.addDep(department);
        if (department.getResult() == 1) {
            return RespBean.ok("新增成功", department);
        }
        return RespBean.error("新增失败");
    }

    /**
     * 修改部门
     * */
    @ApiOperation("修改部门")
    @PostMapping("/update")
    public RespBean update(@RequestBody Department department) {
        if (this.service.update(department) == 1) {
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

    /**
     * 删除部门
     * */
    @ApiOperation("删除部门")
    @DeleteMapping("/{id}")
    public RespBean delete(@PathVariable Integer id) {
        Department department = new Department();
        department.setId(id);
        this.service.deleteDepById(department);
        if (department.getResult() == -2) {
            return RespBean.error("部门下有子部门，删除失败");
        } else if (department.getResult() == -1) {
            return RespBean.error("部门下有员工，删除失败");
        } else if (department.getResult() == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

}
