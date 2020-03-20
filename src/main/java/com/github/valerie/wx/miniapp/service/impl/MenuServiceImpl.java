package com.github.valerie.wx.miniapp.service.impl;

import com.github.valerie.wx.miniapp.model.Menu;
import com.github.valerie.wx.miniapp.mapper.MenuMapper;
import com.github.valerie.wx.miniapp.service.MenuService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (Menu)表服务实现类
 *
 * @author makejava
 * @since 2020-03-19 09:12:23
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
        
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取所有菜单权限
     * */
    @Override
    public List<Menu> getAllMenusWithRole() {
        return this.menuMapper.getAllMenusWithRole();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Menu selectById(Long id) {
        return this.menuMapper.selectById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Menu> selectAllPaging(int offset, int limit) {
        return this.menuMapper.selectAllPaging(offset, limit);
    }
    
    /**
     * 通过实体作为筛选条件查询
     *
     * @param menu 实例对象
     * @return 对象列表
     */
    @Override
    public List<Menu> selectAll(Menu menu) {
        return this.menuMapper.selectAll(menu);
    }
    
    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    @Override
    public List<Menu> select(Map<String, Object> param) {
        return this.menuMapper.select(param);
    }

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    @Override
    public int add(Menu menu) {
                        
        return this.menuMapper.add(menu);
    }

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    @Override
    public int update(Menu menu) {
                        
        return this.menuMapper.update(menu);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(Long id) {
        return this.menuMapper.deleteById(id);
    }
}