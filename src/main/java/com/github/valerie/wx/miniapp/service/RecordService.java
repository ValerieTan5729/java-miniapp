package com.github.valerie.wx.miniapp.service;

import com.github.valerie.wx.miniapp.model.Record;
import java.util.List;
import java.util.Map;

/**
 * 用户的值班打卡记录(Record)表服务接口
 *
 * @author makejava
 * @since 2020-03-26 15:38:00
 */
public interface RecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Record selectById(Long id);

    /**
     * 分页查询
     *
     * @param offset 查询起始页数
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Record> selectAllPaging(int offset, int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param record 实例对象
     * @return 对象列表
     */
    List<Record> selectAll(Record record);

    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    List<Record> select(Map<String, Object> param);

    /**
     * 获取满足条件的条目数
     *
     * @param param 查询条件
     * @return long
     */
    Long count(Map<String, Object> param);

    /**
     * 获取某用户当天在某个值班表下的打卡记录
     * */
    List<Map<String, Object>> checkRecord(Map<String, Object> param, int type);

    /**
     * 新增数据
     *
     * @param record 实例对象
     * @return 影响行数
     */
    int add(Record record);

    /**
     * 修改数据
     *
     * @param record 实例对象
     * @return 影响行数
     */
    int update(Record record);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
