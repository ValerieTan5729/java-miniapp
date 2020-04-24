package com.github.valerie.wx.miniapp.mapper;

import com.github.valerie.wx.miniapp.model.Dictory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * 系统的数字字典(Dictory)表数据库访问层mapper
 *
 * @author makejava
 * @since 2020-03-24 08:57:06
 */
@Component
public interface DictoryMapper {

    /**
     * 通过父节点获取所有数字字典的信息
     * */
    List<Dictory> getAllDictoryByParentId(Long pid);

    /**
     * 获取值班级别
     * */
    List<Dictory> getDutyLevel(Long id);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Dictory selectById(Long id);

    /**
     * 分页查询
     *
     * @param offset 查询起始偏移值
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Dictory> selectAllPaging(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dictory 实例对象
     * @return 对象列表
     */
    List<Dictory> selectAll(Dictory dictory);
    
    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    List<Dictory> select(Map<String, Object> param);
    
    /**
     * 获取满足条件的条目数
     *
     * @param param 查询条件
     * @return int
     */
    Integer count(Map<String, Object> param);

    /**
     * 新增数据
     *
     * @param dictory 实例对象
     * @return 影响行数
     */
    int add(Dictory dictory);

    /**
     * 修改数据
     *
     * @param dictory 实例对象
     * @return 影响行数
     */
    int update(Dictory dictory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
