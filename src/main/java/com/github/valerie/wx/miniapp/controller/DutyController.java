package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.model.Duty;
import com.github.valerie.wx.miniapp.service.DutyService;
import com.github.valerie.wx.miniapp.service.RecordService;
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

@Api("duty(值班表)接口")
@RestController
@RequestMapping("/duty")
@Slf4j
public class DutyController {

    @Autowired
    private DutyService service;

    @Autowired
    private RecordService recordService;

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
     * 获取列表
     * */
    @ApiOperation("获取列表")
    @GetMapping("/")
    public RespBean select(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "limit", defaultValue = "10") int limit,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "beginDate", required = false) String beginDate,
                           @RequestParam(value = "endDate", required = false) String endDate,
                           @RequestParam(value = "sortby", required = false) String sort,
                           @RequestParam(value = "order", required = false) String order) {
        Map<String, Object> param = new HashMap<>();
        param.put("page", (page - 1) * limit);
        param.put("limit", limit);
        param.put("name", name);
        param.put("beginDate", beginDate);
        param.put("endDate", endDate);
        param.put("status", 0);
        if (sort != null && order != null) {
            if (sort.equals("beginDate")) {
                param.put("sort", "Begin_Date");
            } else if (sort.equals("endDate")) {
                param.put("sort", "End_Date");
            }
            if (order.equals("ascending")) {
                param.put("order", "asc");
            } else if (order.equals("descending")) {
                param.put("order", "desc");
            }
        }
        List<Duty> res = this.service.select(param);
        Long total = this.service.count(param);
        return RespBean.ok(new RespPageBean(total, res));
    }

    /**
     * 新增数据
     * */
    @ApiOperation("新增数据")
    @PostMapping("/")
    public RespBean add(@RequestBody Duty duty) {
        duty.setNote(NoteUtils.note(UserUtils.getCurrentUser().getName(), "新增"));
        if (this.service.add(duty) == 1) {
            return RespBean.ok("新增值班表成功");
        }
        return RespBean.ok("新增值班表失败");
    }

    /**
     * 修改数据
     * */
    @ApiOperation("修改数据")
    @PutMapping("/")
    public RespBean update(@RequestBody Duty duty) {
        String note = duty.getNote();
        if (note == null) note = this.service.selectById(duty.getId()).getNote();
        duty.setNote(note + '|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "修改"));
        if (this.service.update(duty) == 1) {
            return RespBean.ok("修改值班表成功");
        }
        return RespBean.error("修改值班表失败");
    }

    /**
     * 删除数据
     * */
    @ApiOperation("删除数据")
    @DeleteMapping("/{id}")
    public RespBean delete(@PathVariable Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put("dutyId", id);
        if (this.recordService.count(param) > 0) {
            return RespBean.error("该值班表下存在值班记录, 无法删除");
        }
        Duty duty = this.service.selectById(id);
        duty.setNote(duty.getNote() + '|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "删除"));
        duty.setStatus(1);
        if (this.service.update(duty) == 1) {
            return RespBean.ok("删除值班表成功");
        }
        return RespBean.error("删除值班表失败");
    }


}
