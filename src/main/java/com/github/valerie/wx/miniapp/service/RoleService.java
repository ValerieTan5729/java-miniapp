package com.github.valerie.wx.miniapp.service;

import com.github.valerie.wx.miniapp.model.Role;
import java.util.List;
import java.util.Map;

/**
 * (Role)表服务接口
 *
 * @author makejava
 * @since 2020-03-19 09:06:27
 */
public interface RoleService {

    /**
     * 通过用户ID查询相应的用户权限
     *
     * @param id 用户ID
     * @return 对象列表
     * */
    List<Role> getUserRolesById(Long id);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Role selectById(Integer id);

    /**
     * 分页查询
     *
     * @param offset 查询起始页数
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Role> selectAllPaging(int offset, int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param role 实例对象
     * @return 对象列表
     */
    List<Role> selectAll(Role role);
    
    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    List<Role> select(Map<String, Object> param);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 影响行数
     */
    int add(Role role);

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 影响行数
     */
    int update(Role role);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
