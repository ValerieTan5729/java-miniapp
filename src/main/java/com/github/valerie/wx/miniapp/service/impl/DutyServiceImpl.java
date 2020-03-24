package com.github.valerie.wx.miniapp.service.impl;

import com.github.valerie.wx.miniapp.model.Duty;
import com.github.valerie.wx.miniapp.mapper.DutyMapper;
import com.github.valerie.wx.miniapp.service.DutyService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 值班表信息(Duty)表服务实现类
 *
 * @author makejava
 * @since 2020-03-23 11:10:31
 */
@Service("dutyService")
public class DutyServiceImpl implements DutyService {
        
    @Autowired
    private DutyMapper dutyMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Duty selectById(Long id) {
        return this.dutyMapper.selectById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Duty> selectAllPaging(int offset, int limit) {
        return this.dutyMapper.selectAllPaging(offset, limit);
    }
    
    /**
     * 通过实体作为筛选条件查询
     *
     * @param duty 实例对象
     * @return 对象列表
     */
    @Override
    public List<Duty> selectAll(Duty duty) {
        return this.dutyMapper.selectAll(duty);
    }
    
    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    @Override
    public List<Duty> select(Map<String, Object> param) {
        return this.dutyMapper.select(param);
    }

    /**
     * 获取满足条件的条目数
     *
     * @param param 查询条件
     * @return int
     */
    @Override
    public Integer count(Map<String, Object> param) {
        return this.dutyMapper.count(param);
    }

    /**
     * 新增数据
     *
     * @param duty 实例对象
     * @return 影响行数
     */
    @Override
    public int add(Duty duty) {
                        
        return this.dutyMapper.add(duty);
    }

    /**
     * 修改数据
     *
     * @param duty 实例对象
     * @return 影响行数
     */
    @Override
    public int update(Duty duty) {
                        
        return this.dutyMapper.update(duty);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(Long id) {
        return this.dutyMapper.deleteById(id);
    }
}
