package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.model.Dictory;
import com.github.valerie.wx.miniapp.service.DictoryService;
import com.github.valerie.wx.miniapp.utils.NoteUtils;
import com.github.valerie.wx.miniapp.utils.UserUtils;
import com.github.valerie.wx.miniapp.utils.response.RespBean;
import com.github.valerie.wx.miniapp.utils.response.RespPageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("dictory")
@RestController
@RequestMapping("/basic/dic")
@Slf4j
public class DictoryController {

    @Autowired
    private DictoryService service;

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
     * 获取所有信息
     * */
    @ApiOperation("获取所有信息")
    @GetMapping("/")
    public RespBean select() {
        return RespBean.ok("获取成功", this.service.getAllDictory());
    }

    /**
     * 通过父节点获取下属数字字典的信息
     * */
    @ApiOperation("通过父节点获取下属数字字典的信息")
    @GetMapping("/parent/{id}")
    public RespBean selectByParentId(@PathVariable Long id) {
        return RespBean.ok("获取成功", this.service.getAllDictoryByParentId(id));
    }


    /**
     * 新增数据
     * */
    @ApiOperation("新增数据")
    @PostMapping("/add")
    public RespBean add(@RequestBody Dictory dictory) {
        dictory.setNote(NoteUtils.note(UserUtils.getCurrentUser().getName(), "新增"));
        if (this.service.add(dictory) == 1) {
            return RespBean.ok("新增成功");
        }
        return RespBean.error("新增失败");
    }

    /**
     * 修改数据
     * */
    @ApiOperation("修改数据")
    @PostMapping("/update")
    public RespBean update(@RequestBody Dictory dictory) {
        String note = dictory.getNote();
        if (note == null) {
            note = this.service.selectById(dictory.getId()).getNote();
        }
        dictory.setNote(note + '|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "修改"));
        if (this.service.update(dictory) == 1) {
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

    /**
     * 删除数据
     * */
    @ApiOperation("删除数据")
    @DeleteMapping("/{id}")
    public RespBean delete(@PathVariable Long id) {
        List<Dictory> children = this.service.getAllDictoryByParentId(id);
        if (children.size() != 0) {
            return RespBean.error("该项有子项, 无法删除");
        } else {
            Dictory dictory = this.service.selectById(id);
            dictory.setNote(dictory.getNote() + '|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "删除"));
            dictory.setStatus(1);
            if (this.service.update(dictory) == 1) {
                return RespBean.ok("删除成功");
            }
            return RespBean.error("删除失败");
        }
    }

}
