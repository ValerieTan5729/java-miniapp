package com.github.valerie.wx.miniapp.service.impl;

import com.github.valerie.wx.miniapp.mapper.DictoryMapper;
import com.github.valerie.wx.miniapp.model.Record;
import com.github.valerie.wx.miniapp.mapper.RecordMapper;
import com.github.valerie.wx.miniapp.service.RecordService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户的值班打卡记录(Record)表服务实现类
 *
 * @author makejava
 * @since 2020-03-26 15:38:00
 */
@Service("recordService")
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private DictoryMapper dictoryMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Record selectById(Long id) {
        return this.recordMapper.selectById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Record> selectAllPaging(int offset, int limit) {
        return this.recordMapper.selectAllPaging(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param record 实例对象
     * @return 对象列表
     */
    @Override
    public List<Record> selectAll(Record record) {
        return this.recordMapper.selectAll(record);
    }

    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    @Override
    public List<Record> select(Map<String, Object> param) {
        return this.recordMapper.select(param);
    }

    /**
     * 获取满足条件的条目数
     *
     * @param param 查询条件
     * @return long
     */
    @Override
    public Long count(Map<String, Object> param) {
        return this.recordMapper.count(param);
    }

    @Override
    public List<Map<String, Object>> checkRecord(Map<String, Object> param, int type) {
        String place = type == 0 ? "(3,4,5,6)" : "(7,8,9,10)";
        param.put("place", place);
        // List<Map<String, Object>> res = this.recordMapper.checkUserRecord(param);
        return this.recordMapper.checkUserRecord(param);
    }

    /**
     * 新增数据
     *
     * @param record 实例对象
     * @return 影响行数
     */
    @Override
    public int add(Record record) {
        return this.recordMapper.add(record);
    }

    /**
     * 修改数据
     *
     * @param record 实例对象
     * @return 影响行数
     */
    @Override
    public int update(Record record) {
        return this.recordMapper.update(record);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(Long id) {
        return this.recordMapper.deleteById(id);
    }
}
