package com.github.valerie.wx.miniapp.service;

import com.github.valerie.wx.miniapp.model.Menu;
import java.util.List;
import java.util.Map;

/**
 * (Menu)表服务接口
 *
 * @author makejava
 * @since 2020-03-19 09:12:23
 */
public interface MenuService {

    /**
     * 通过Parent_ID查询所有部门信息
     *
     * @param pid parentId
     * @return 对象列表
     */
    List<Menu> getAllMenusByParentId(Integer pid);

    /**
     * 获取所有菜单权限
     * */
    List<Menu> getAllMenusWithRole();

    /**
     * 根据用户ID获取相应的菜单
     * */
    Menu getMenuByUserID(Long userId);

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
     * @param offset 查询起始页数
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Menu> selectAllPaging(int offset, int limit);


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

    /**
     * 为相应的角色添加相应的菜单列表
     *
     * @param roleId 角色ID
     * @param menuList 菜单列表
     * @return 是否成功
     * */
    boolean updateMenuRole(Long roleId, List<Long> menuList);

}
