package com.github.valerie.wx.miniapp.mapper;

import com.github.valerie.wx.miniapp.model.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * (Menu)表数据库访问层mapper
 *
 * @author makejava
 * @since 2020-03-19 09:12:23
 */
@Component
public interface MenuMapper {

    /**
     * 获取所有菜单权限
     * */
    List<Menu> getAllMenusWithRole();

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Menu selectById(Long id);

    /**
     * 分页查询
     *
     * @param offset 查询起始偏移值
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Menu> selectAllPaging(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param menu 实例对象
     * @return 对象列表
     */
    List<Menu> selectAll(Menu menu);
    
    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    List<Menu> select(Map<String, Object> param);

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    int add(Menu menu);

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    int update(Menu menu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
