package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.model.Role;
import com.github.valerie.wx.miniapp.service.MenuService;
import com.github.valerie.wx.miniapp.service.RoleService;
import com.github.valerie.wx.miniapp.utils.NoteUtils;
import com.github.valerie.wx.miniapp.utils.UserUtils;
import com.github.valerie.wx.miniapp.utils.response.RespBean;
import com.github.valerie.wx.miniapp.utils.response.RespPageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("role接口")
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService service;

    @Autowired
    private MenuService menuService;

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
     * 通过Map作为筛选条件查询
     *
     * @return 对象列表
     */
    @ApiOperation("条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name="name", value="角色名", defaultValue="admin")
    })
    @GetMapping("/")
    public RespBean select(@RequestParam(value = "name", required = false) String name) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", 0);
        param.put("name", name);
        List<Role> res = this.service.select(param);
        Integer total = this.service.count(param);
        return RespBean.ok(new RespPageBean(total, res));
        // return RespBean.ok("获取成功", new RespPageBean(total, res));
    }

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增角色信息")
    @PostMapping("/add")
    public RespBean add(@RequestBody Role role) {
        role.setNote(NoteUtils.note(UserUtils.getCurrentUser().getName(), "新增"));
        if (this.service.add(role) == 1) {
            return RespBean.error("新增角色成功");
        }
        return RespBean.error("新增角色失败");
    }

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @ApiOperation("修改角色信息")
    @PostMapping("/update")
    public RespBean update(@RequestBody Role role) {
        String note = role.getNote();
        if (note == null) {
            note = this.service.selectById(role.getId()).getNote();
        }
        role.setNote(note + "|" + NoteUtils.note(UserUtils.getCurrentUser().getName(), "修改"));
        if (this.service.update(role) == 1) {
            return RespBean.ok("修改角色成功");
        }
        return RespBean.error("修改角色失败");
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("删除角色")
    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable Long id) {
        Role role = this.service.selectById(id);
        role.setNote(role.getNote() + "|" + NoteUtils.note(UserUtils.getCurrentUser().getName(), "删除"));
        role.setStatus(1);
        if (this.service.update(role) == 1) {
            return RespBean.ok("删除角色成功");
        }
        return RespBean.error("删除角色失败");
    }

    /**
     * 给角色分配相应的菜单列表
     * */
    @ApiOperation("给角色分配相应的菜单列表")
    @PutMapping("/menu")
    public RespBean updateRoleMenu(@RequestParam("roleId") Long roleId, @RequestParam("menuList") List<Long> menuList) {
        if (this.menuService.updateMenuRole(roleId, menuList)) {
            return RespBean.ok("分配成功");
        }
        return RespBean.error("分配失败");
    }
}
