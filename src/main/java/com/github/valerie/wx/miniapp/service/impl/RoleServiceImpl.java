package com.github.valerie.wx.miniapp.service.impl;

import com.github.valerie.wx.miniapp.mapper.UserRoleMapper;
import com.github.valerie.wx.miniapp.model.Role;
import com.github.valerie.wx.miniapp.mapper.RoleMapper;
import com.github.valerie.wx.miniapp.service.RoleService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2020-03-19 09:06:27
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
        
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 通过用户ID查询相应的用户权限
     *
     * @param id 用户ID
     * @return 对象列表
     * */
    @Override
    public List<Role> getUserRolesById(Long id) {
        return this.roleMapper.getUserRolesById(id);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Role selectById(Long id) {
        return this.roleMapper.selectById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Role> selectAllPaging(int offset, int limit) {
        return this.roleMapper.selectAllPaging(offset, limit);
    }
    
    /**
     * 通过实体作为筛选条件查询
     *
     * @param role 实例对象
     * @return 对象列表
     */
    @Override
    public List<Role> selectAll(Role role) {
        return this.roleMapper.selectAll(role);
    }
    
    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    @Override
    public List<Role> select(Map<String, Object> param) {
        return this.roleMapper.select(param);
    }

    @Override
    public Integer count(Map<String, Object> param) {
        return this.roleMapper.count(param);
    }

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 影响行数
     */
    @Override
    public int add(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return this.roleMapper.add(role);
    }

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 影响行数
     */
    @Override
    public int update(Role role) {
                        
        return this.roleMapper.update(role);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(Long id) {
        return this.roleMapper.deleteById(id);
    }
}
