package com.github.valerie.wx.miniapp.service.impl;

import com.github.valerie.wx.miniapp.model.Role;
import com.github.valerie.wx.miniapp.model.User;
import com.github.valerie.wx.miniapp.mapper.UserMapper;
import com.github.valerie.wx.miniapp.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

/**
 * 用户信息(User)表服务实现类
 *
 * @author makejava
 * @since 2020-03-17 14:42:54
 */
@Service("userService")
public class UserServiceImpl implements UserService {
        
    @Autowired
    private UserMapper userMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User selectById(Long id) {
        return this.userMapper.selectById(id);
    }

    @Override
    public User selectIdWithRole(Long id) {
        return this.userMapper.selectIdWithRole(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> selectAllPaging(int offset, int limit) {
        return this.userMapper.selectAllPaging(offset, limit);
    }
    
    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    @Override
    public List<User> selectAll(User user) {
        return this.userMapper.selectAll(user);
    }
    
    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    @Override
    public List<User> select(Map<String, Object> param) {
        return this.userMapper.select(param);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    @Override
    public int add(User user) {
                        
        return this.userMapper.add(user);
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    @Override
    public int update(User user) {
                        
        return this.userMapper.update(user);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(Long id) {
        return this.userMapper.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = this.userMapper.loadUserByPhone(phone);
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在!");
        }
        user.setRoles(this.userMapper.getUserRolesById(user.getId()));
        return user;
    }
}
