package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.model.Menu;
import com.github.valerie.wx.miniapp.service.MenuService;
import com.github.valerie.wx.miniapp.utils.NoteUtils;
import com.github.valerie.wx.miniapp.utils.UserUtils;
import com.github.valerie.wx.miniapp.utils.response.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("menu接口")
@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    @Autowired
    private MenuService service;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过ID查询单条数据")
    @ApiImplicitParam(name="id", value="ID", defaultValue="1", required=true)
    @GetMapping("/{id}")
    public RespBean selectOne(@PathVariable Long id) {
        return RespBean.ok("获取成功", this.service.selectById(id));
    }

    /**
     * 通过Parent_ID查询下一级菜单列表
     */
    @ApiOperation("通过Parent_ID查询下一级菜单列表")
    @GetMapping("/parent/{id}")
    public RespBean selectByParentId(@PathVariable Long id) {
        return RespBean.ok("获取成功", this.service.getAllMenusByParentId(id));
    }

    /**
     * 获取所有菜单
     * */
    @ApiOperation("获取所有菜单")
    @GetMapping("/all")
    public RespBean select() {
        return RespBean.ok(this.service.getAllMenusByParentId((long) 0));
        // return RespBean.ok("获取成功", this.service.getAllMenusByParentId((long) 0));
    }

    /**
     * 通过用户获取相应的菜单列表
     * */
    @ApiOperation("通过用户获取相应的菜单列表")
    @GetMapping("/")
    public RespBean selectByUser() {
        return RespBean.ok(this.service.getMenuByUser());
        // return RespBean.ok("菜单获取成功", this.service.getMenuByUser());
    }

    /**
     * 通过用户ID获取相应的菜单列表
     * */
    @ApiOperation("通过用户ID获取相应的菜单列表")
    @GetMapping("/user/{id}")
    public RespBean selectByUserId(@PathVariable Long id) {
        return RespBean.ok("获取成功", this.service.getMenuByUserID(id));
    }

    /**
     * 根据角色ID获取菜单ID
     * */
    @ApiOperation("根据角色ID获取菜单ID")
    @GetMapping("/role/{id}")
    public RespBean selectByRoleId(@PathVariable Long id) {
        return RespBean.ok(this.service.getMenuIdByRoleId(id));
        // return RespBean.ok("获取成功", this.service.getMenuIdByRoleId(id));
    }

    /**
     * 新增菜单
     * */
    @ApiOperation("新增菜单")
    @PostMapping("/add")
    public RespBean add(@RequestBody Menu menu) {
        menu.setNote(NoteUtils.note(UserUtils.getCurrentUser().getName(), "新增"));
        if (this.service.add(menu) == 1) {
            return RespBean.ok("新增成功");
        }
        return RespBean.error("新增失败");
    }

    /**
     * 修改菜单
     * */
    @ApiOperation("修改菜单")
    @PostMapping("/update")
    public RespBean update(@RequestBody Menu menu) {
        String note = menu.getNote();
        if (note == null) {
            note = this.service.selectById(menu.getId()).getNote();
        }
        menu.setNote(note + '|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "修改"));
        if (this.service.update(menu) == 1) {
            return RespBean.ok("修改菜单成功");
        }
        return RespBean.error("修改菜单失败");
    }

    /**
     * 删除菜单
     * */
    @ApiOperation("删除菜单")
    @DeleteMapping("/{id}")
    public RespBean delete(@PathVariable Long id) {
        Menu menu = this.service.selectById(id);
        menu.setNote(menu.getNote() + '|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "删除"));
        menu.setStatus(1);
        if (this.service.update(menu) == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

}
