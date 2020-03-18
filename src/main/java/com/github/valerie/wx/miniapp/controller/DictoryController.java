package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.model.Dictory;
import com.github.valerie.wx.miniapp.service.DictoryService;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统的数字字典(Dictory)表控制层
 *
 * @author makejava
 * @since 2020-03-18 15:21:28
 */
@Api("dictory接口")
@RestController
@RequestMapping("/dictory")
public class DictoryController {
    
    @Autowired
    private DictoryService dictoryService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过ID查询单条数据")
    @ApiImplicitParam(name="id", value="ID", defaultValue="1", required=true)
    @GetMapping("/select/{id}")
    public Dictory selectOne(@PathVariable Long id) {
        return this.dictoryService.selectById(id);
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
    public List<Dictory> selectAllPaging(@PathVariable int page, @PathVariable int limit) {
        int offset = (page - 1) * limit;
        return this.dictoryService.selectAllPaging(offset, limit);
    }
    
    /**
     * 通过实体作为筛选条件查询
     *
     * @param dictory 实例对象
     * @return 对象列表
     */
    @ApiOperation("通过实体作为筛选条件查询")
    @ApiImplicitParam(name="", value="", defaultValue="", required=true)
    @PostMapping("/select/model")
    public List<Dictory> selectAll(Dictory dictory) {
        return this.dictoryService.selectAll(dictory);
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
    public List<Dictory> select(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        Map<String, Object> param = new HashMap<>();
        param.put("page", (page - 1) * limit);
        param.put("limit", limit);
        return this.dictoryService.select(param);
    }
    
    /**
     * 新增数据
     *
     * @param dictory 实例对象
     * @return 实例对象
     */
    @PostMapping("/add")
    public Dictory add(@RequestBody Dictory dictory) {
        this.dictoryService.add(dictory);
        return dictory;
    }

    /**
     * 修改数据
     *
     * @param dictory 实例对象
     * @return 实例对象
     */
    @PostMapping("/update")
    public Dictory update(@RequestBody Dictory dictory) {
        this.dictoryService.update(dictory);
        return this.dictoryService.selectById(dictory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id) {
        return this.dictoryService.deleteById(id) > 0;
    }

}
