package com.github.valerie.wx.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.github.valerie.wx.miniapp.config.WxMaConfiguration;
import com.github.valerie.wx.miniapp.model.User;
import com.github.valerie.wx.miniapp.service.UserService;
import com.github.valerie.wx.miniapp.utils.NoteUtils;
import com.github.valerie.wx.miniapp.utils.ScanQrCodeUtils;
import com.github.valerie.wx.miniapp.utils.UserUtils;
import com.github.valerie.wx.miniapp.utils.response.RespBean;
import com.github.valerie.wx.miniapp.utils.response.RespPageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2020-03-03 15:36:17
 */
@Api("user接口")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

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
        return RespBean.ok("获取成功", this.userService.selectById(id));
    }

    /**
     * 通过Map作为筛选条件查询
     *
     * @param page 页数
     * @param limit 条数
     * @return 对象列表
     */
    @ApiOperation("条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name="page", value="页码", defaultValue="1", required=true),
        @ApiImplicitParam(name="limit", value="每页展示的条数", defaultValue="10", required=true),
        @ApiImplicitParam(name="name", value="用户名", defaultValue="admin"),
        @ApiImplicitParam(name="phone", value="用户手机号码", defaultValue="156"),
        @ApiImplicitParam(name="depId", value="用户所属部门的ID", defaultValue="2")
    })
    @GetMapping("/")
    public RespBean select(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "limit", defaultValue = "10") int limit,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "phone", required = false) String phone,
                           @RequestParam(value = "depId", required = false) Integer depId,
                           @RequestParam(value = "levelId", required = false) Integer levelId) {
        log.info("name : {}", name);
        Map<String, Object> param = new HashMap<>();
        param.put("page", (page - 1) * limit);
        param.put("limit", limit);
        param.put("status", 0);
        param.put("name", name);
        param.put("phone", phone);
        param.put("depId", depId);
        param.put("dutyLevelId", levelId);
        List<User> res = this.userService.select(param);
        Long total = this.userService.count(param);
        return RespBean.ok("获取成功", new RespPageBean(total, res));
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增用户数据")
    @PostMapping("/")
    public RespBean add(@RequestBody User user) {
        User exist = this.userService.findUserByPhone(user.getPhone());
        if (exist != null) {
            return RespBean.error("手机号码已关联用户" + exist.getName() + ", 请修改手机号码");
        }
        if (user.getPassword() != null) {
            // 密码加盐处理
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        } else {
            // 用户初始密码为123456
            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        }
        user.setNote(NoteUtils.note(UserUtils.getCurrentUser().getName(), "新增"));
        if (this.userService.add(user) == 1) {
            return RespBean.ok("新增用户信息成功");
        }
        return RespBean.error("新增用户信息失败");
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @ApiOperation("修改用户基本信息")
    @PutMapping("/")
    public RespBean update(@RequestBody User user) {
        User exist = this.userService.findUserByPhone(user.getPhone());
        if (exist != null && !exist.getId().equals(user.getId())) {
            return RespBean.error("手机号码已关联用户" + exist.getName() + ", 请修改手机号码");
        }
        String note = user.getNote();
        if (note == null) {
            note = this.userService.selectById(user.getId()).getNote();
        }
        user.setNote(note + '|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "修改基本信息"));
        if (this.userService.update(user) == 1) {
            return RespBean.ok("修改用户信息成功", this.userService.selectById(user.getId()));
        }
        return RespBean.error("更新用户信息失败");
    }

    /**
     * 修改用户密码
     *
     * @return 实例对象
     */
    @ApiOperation("修改用户密码")
    @PutMapping("/{id}/password")
    public RespBean updatePassword(@PathVariable Long id, @RequestBody Map<String, String> pass) {
        User user = this.userService.selectById(id);
        String password = this.userService.selectPasswordById(id);
        // log.info("oldPassword:{}, newPassword:{}", pass.get("oldPassword"), pass.get("newPassword"));
        // log.info("user password is {}", password);
        if (new BCryptPasswordEncoder().matches(pass.get("oldPassword"), password)) {
            user.setPassword(new BCryptPasswordEncoder().encode(pass.get("newPassword")));
            user.setNote(user.getNote() + '|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "修改密码"));
            if (this.userService.update(user) == 1) {
                return RespBean.ok("修改密码成功");
            } else {
                return RespBean.error("修改密码失败");
            }
        } else {
            return RespBean.error("旧密码错误");
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("禁用用户")
    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable Long id) {
        User user = this.userService.selectById(id);
        user.setNote(user.getNote() + '|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "删除"));
        user.setStatus(1);
        if (this.userService.update(user) == 1) {
            return RespBean.ok("删除用户成功");
        }
        return RespBean.error("删除用户失败");
    }

    /**
     * 批量删除用户数据
     * */
    @ApiOperation("批量删除用户")
    @DeleteMapping("/batch/{ids}")
    public RespBean batchDelete(@PathVariable String ids) {
        List<Long> list = Arrays.stream(ids.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        List<User> userList = new ArrayList<>();
        for (Long id : list) {
            User user = this.userService.selectById(id);
            user.setNote(user.getNote() + '|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "删除"));
            user.setStatus(1);
            userList.add(user);
        }
        for (int index = 0; index < userList.size(); index++) {
            if (this.userService.update(userList.get(index)) != 1) {
                System.out.println("更新用户信息失败");
                for (int i = 0; i < index; i++) {
                    User user = userList.get(i);
                    user.setStatus(0);
                    user.setNote(user.getNote().replace('|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "删除"), ""));
                    this.userService.update(user);
                }
                return RespBean.error("批量删除用户失败");
            }
        }
        return RespBean.ok("批量删除用户成功");
    }

    /**
     * 给用户分配权限
     *
     * @param userId 用户ID
     * @param roleList 角色ID列表
     * @return 实例对象
     * */
    @ApiOperation("给用户分配权限")
    @PutMapping("/role")
    public RespBean updateUserRole(@RequestParam("userId") Long userId, @RequestParam("roleList") List<Long> roleList) {
        log.info("userId : {}, roleList : {}", userId, roleList);
        if (this.userService.updateUserRole(userId, roleList)) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

}
