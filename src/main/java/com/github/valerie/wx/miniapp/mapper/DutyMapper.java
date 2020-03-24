package com.github.valerie.wx.miniapp.mapper;

import com.github.valerie.wx.miniapp.model.Duty;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * 值班表信息(Duty)表数据库访问层mapper
 *
 * @author makejava
 * @since 2020-03-23 11:10:31
 */
@Component
public interface DutyMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Duty selectById(Long id);

    /**
     * 分页查询
     *
     * @param offset 查询起始偏移值
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Duty> selectAllPaging(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param duty 实例对象
     * @return 对象列表
     */
    List<Duty> selectAll(Duty duty);
    
    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    List<Duty> select(Map<String, Object> param);

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
     * @param duty 实例对象
     * @return 影响行数
     */
    int add(Duty duty);

    /**
     * 修改数据
     *
     * @param duty 实例对象
     * @return 影响行数
     */
    int update(Duty duty);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
