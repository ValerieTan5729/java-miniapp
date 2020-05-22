package com.github.valerie.wx.miniapp.service.impl;

import com.github.valerie.wx.miniapp.model.Dictory;
import com.github.valerie.wx.miniapp.mapper.DictoryMapper;
import com.github.valerie.wx.miniapp.service.DictoryService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 系统的数字字典(Dictory)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 08:57:06
 */
@Service("dictoryService")
public class DictoryServiceImpl implements DictoryService {
        
    @Autowired
    private DictoryMapper dictoryMapper;

    /**
     * 获取所有信息
     * */
    @Override
    public List<Dictory> getAllDictory() {
        return this.dictoryMapper.getAllDictoryByParentId((long) 0);
    }

    /**
     * 通过父节点获取所有数字字典的信息
     * */
    @Override
    public List<Dictory> getAllDictoryByParentId(Long pid) {
        return this.dictoryMapper.getAllDictoryByParentId(pid);
    }

    /**
     * 获取值班级别
     * */
    @Override
    public List<Dictory> getDutyLevel(Long id) {
        return this.dictoryMapper.getDutyLevel(id);
    }

    /**
     * 获取地点名称列表
     * */
    @Override
    public List<String> getPlaceList(Long id) {
        return this.dictoryMapper.getPlaceList(id);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Dictory selectById(Long id) {
        return this.dictoryMapper.selectById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Dictory> selectAllPaging(int offset, int limit) {
        return this.dictoryMapper.selectAllPaging(offset, limit);
    }
    
    /**
     * 通过实体作为筛选条件查询
     *
     * @param dictory 实例对象
     * @return 对象列表
     */
    @Override
    public List<Dictory> selectAll(Dictory dictory) {
        return this.dictoryMapper.selectAll(dictory);
    }
    
    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    @Override
    public List<Dictory> select(Map<String, Object> param) {
        return this.dictoryMapper.select(param);
    }
    
    /**
     * 获取满足条件的条目数
     *
     * @param param 查询条件
     * @return int
     */
    @Override
    public Integer count(Map<String, Object> param) {
        return this.dictoryMapper.count(param);
    }

    /**
     * 新增数据
     *
     * @param dictory 实例对象
     * @return 影响行数
     */
    @Override
    public int add(Dictory dictory) {
                        
        return this.dictoryMapper.add(dictory);
    }

    /**
     * 修改数据
     *
     * @param dictory 实例对象
     * @return 影响行数
     */
    @Override
    public int update(Dictory dictory) {
                        
        return this.dictoryMapper.update(dictory);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(Long id) {
        return this.dictoryMapper.deleteById(id);
    }
}
