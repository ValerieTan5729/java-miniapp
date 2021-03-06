package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.model.Record;
import com.github.valerie.wx.miniapp.service.RecordService;
import com.github.valerie.wx.miniapp.utils.FileUtils;
import com.github.valerie.wx.miniapp.utils.NoteUtils;
import com.github.valerie.wx.miniapp.utils.UserUtils;
import com.github.valerie.wx.miniapp.utils.response.RespBean;
import com.github.valerie.wx.miniapp.utils.response.RespPageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("record(打卡记录)接口")
@RestController
@RequestMapping("/record")
@Slf4j
public class RecordController {

    @Autowired
    private RecordService service;

    /**
     * 通过主键查询单条数据
     * */
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
                           @RequestParam(value = "userId", required = false) Long userId,
                           @RequestParam(value = "dutyId", required = false) Long dutyId,
                           @RequestParam(value = "beginDate", required = false) String beginDate,
                           @RequestParam(value = "endDate", required = false) String endDate,
                           @RequestParam(value = "place", required = false) String place,
                           @RequestParam(value = "level", required = false) Long level,
                           @RequestParam(value = "date", required = false) String date,
                           @RequestParam(value = "sortby", required = false) String sort,
                           @RequestParam(value= "order", required = false) String order) {
        Map<String, Object> param = new HashMap<>();
        param.put("page", (page - 1) * limit);
        param.put("limit", limit);
        param.put("status", 0);
        param.put("userId", userId);
        param.put("dutyId", dutyId);
        param.put("beginDate", beginDate);
        param.put("endDate", endDate);
        param.put("place", place);
        param.put("level", level);
        param.put("date", date);
        if (sort != null && order != null) {
            param.put("sort", "Date");
            if (order.equals("ascending")) {
                param.put("order", "asc");
            } else if (order.equals("descending")) {
                param.put("order", "desc");
            }
        }
        List<Record> res = this.service.select(param);
        Long total = this.service.count(param);
        return RespBean.ok("获取成功", new RespPageBean(total, res));
    }

    /**
     * 获取值班表下的打卡记录
     * */
    @ApiOperation("获取值班表下的打卡记录")
    @GetMapping("/duty/{id}")
    public RespBean selectByDutyId(@PathVariable Long id,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Map<String, Object> param = new HashMap<>();
        param.put("page", (page - 1) * limit);
        param.put("limit", limit);
        param.put("dutyId", id);
        param.put("status", 0);
        List<Record> res = this.service.select(param);
        Long total = this.service.count(param);
        return RespBean.ok("成功获取该值班表的打卡记录", new RespPageBean(total, res));
    }

    /**
     * 获取某用户下的打卡记录
     * */
    @ApiOperation("获取某用户下的打卡记录")
    @GetMapping("/user/{id}")
    public RespBean selectByUserId(@PathVariable Long id,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "limit", defaultValue = "100") int limit) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", id);
        param.put("page", (page - 1) * limit);
        param.put("limit", limit);
        param.put("status", 0);
        List<Record> res = this.service.select(param);
        Long total = this.service.count(param);
        return RespBean.ok("成功获取该用户下的打卡记录", new RespPageBean(total, res));
    }

    /**
     * 获取某用户当天在某个值班表下的打卡记录
     * */
    @ApiOperation("获取某用户当天在某个值班表下的打卡记录")
    @GetMapping("/check/{type}")
    public RespBean checkRecord(@PathVariable("type") int type,
                                @RequestParam("userId") Long userId,
                                @RequestParam("dutyId") Long dutyId) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("dutyId", dutyId);
        param.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        List<Map<String, Object>> res = this.service.checkRecord(param, type);
        res.forEach(item -> System.out.println(item.toString()));
        return RespBean.ok(res);
    }

    /**
     * 新增打卡数据
     * */
    /*
    @ApiOperation("新增数据")
    @PostMapping("/add")
    public RespBean add(@RequestParam("userId") Long userId,
                        @RequestParam("dutyId") Long dutyId,
                        @RequestParam("place") Integer place,
                        @RequestParam("remark") String remark,
                        @RequestParam("img") MultipartFile img) {
        if (img.isEmpty()) {
            return RespBean.error("接收图片出错");
        }

        Record record = new Record();
        record.setUserId(userId);
        record.setDutyId(dutyId);
        record.setPlace(String.valueOf(place));
        record.setRemark(remark);
        return null;
    }*/

    /**
     * 新增数据
     * */
    @ApiOperation("新增数据")
    @PostMapping("/add")
    public RespBean add(@RequestBody Record record) {
        record.setDate(new Date());
        record.setNote(NoteUtils.note(UserUtils.getCurrentUser().getName(), "新增"));
        if (this.service.add(record) == 1) {
            return RespBean.ok("打卡成功");
        }
        return RespBean.error("打卡失败");
    }

    /**
     * 删除数据
     * */
    @ApiOperation("删除数据")
    @DeleteMapping("/{id}")
    public RespBean delete(@PathVariable Long id) {
        Record record = this.service.selectById(id);
        record.setNote(record.getNote() + '|' + NoteUtils.note(UserUtils.getCurrentUser().getName(), "删除"));
        record.setStatus(1);
        if (this.service.update(record) == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    // 图片在线预览
    @ApiOperation("打卡图片在线预览")
    @GetMapping(value = "/img/", produces = MediaType.IMAGE_PNG_VALUE)
    public Object preview(@RequestParam("path") String path) throws FileNotFoundException {
        // String path = this.recordService.selectById(id).getImgPath();
        HttpHeaders headers = new HttpHeaders();
        log.info("path:{}", path);
        if (path != null) {
            InputStream input = new FileInputStream(new File(FileUtils.getUploadDir() + path));
            InputStreamResource resource = new InputStreamResource(input);
            // HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        headers.set("message", "no file found");
        return new ResponseEntity(headers, HttpStatus.OK);
    }

}
