package com.github.valerie.wx.miniapp.mapper;

import com.github.valerie.wx.miniapp.model.Role;
import com.github.valerie.wx.miniapp.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * 用户信息(User)表数据库访问层mapper
 *
 * @author makejava
 * @since 2020-03-17 14:42:54
 */
@Component
public interface UserMapper {

    User loadUserByPhone(String phone);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User selectById(Long id);

    String selectPasswordById(Long id);

    User selectIdWithRole(Long id);

    List<Role> getUserRolesById(Long id);

    /**
     * 分页查询
     *
     * @param offset 查询起始偏移值
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> selectAllPaging(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<User> selectAll(User user);

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

}
