package com.github.valerie.wx.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.github.valerie.wx.miniapp.config.WxMaConfiguration;
import com.github.valerie.wx.miniapp.model.User;
import com.github.valerie.wx.miniapp.service.UserService;
import com.github.valerie.wx.miniapp.utils.ScanQrCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/select/{id}")
    public User selectOne(@PathVariable Long id) {
        return this.userService.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param page 查询起始页数
     * @param limit 查询条数
     * @return 对象列表
     */
    @ApiOperation("分页查询数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name="page", value="页码", defaultValue="1", required=true),
        @ApiImplicitParam(name="limit", value="每页展示的条数", defaultValue="10", required=true)
    })
    @GetMapping("/page/{page}/{limit}")
    public List<User> selectAllPaging(@PathVariable int page, @PathVariable int limit) {
        int offset = (page - 1) * limit;
        return this.userService.selectAllPaging(offset, limit);
    }
    
    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    @ApiOperation("通过实体作为筛选条件查询")
    @ApiImplicitParam(name="", value="", defaultValue="", required=true)
    @PostMapping("/select/model")
    public List<User> selectAll(User user) {
        return this.userService.selectAll(user);
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
            @ApiImplicitParam(name="limit", value="每页展示的条数", defaultValue="10", required=true)
    })
    @GetMapping("/")
    public List<User> select(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        Map<String, Object> param = new HashMap<>();
        param.put("page", (page - 1) * limit);
        param.put("limit", limit);
        return this.userService.select(param);
    }
    
    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @PostMapping("/add")
    public User add(@RequestBody User user) {
        this.userService.add(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @PostMapping("/update")
    public User update(@RequestBody User user) {
        log.info("user update info : {}", user.toString());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        this.userService.update(user);
        return this.userService.selectById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id) {
        return this.userService.deleteById(id) > 0;
    }

}
