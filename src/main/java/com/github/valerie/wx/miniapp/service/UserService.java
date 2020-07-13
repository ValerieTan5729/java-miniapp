package com.github.valerie.wx.miniapp.service;

import com.github.valerie.wx.miniapp.model.Role;
import com.github.valerie.wx.miniapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

/**
 * 用户信息(User)表服务接口
 *
 * @author makejava
 * @since 2020-03-17 14:42:54
 */
public interface UserService extends UserDetailsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User selectById(Long id);

    String selectPasswordById(Long id);

    User selectIdWithRole(Long id);

    /**
     * 分页查询
     *
     * @param offset 查询起始页数
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> selectAllPaging(int offset, int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<User> selectAll(User user);

    /**
     * 通过手机号码查询用户
     * */
    User findUserByPhone(String phone);

    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    List<User> select(Map<String, Object> param);

    /**
     * 总行数
     *
     * @param param 查询条件
     * @return 总行数
     * */
    Long count(Map<String, Object> param);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int add(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 为相应的用户添加相应的角色列表
     *
     * @param userId 用户ID
     * @param roleList 角色列表
     * @return 是否成功
     * */
    boolean updateUserRole(Long userId, List<Long> roleList);

}
