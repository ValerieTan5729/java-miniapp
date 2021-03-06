package com.github.valerie.wx.miniapp.service.impl;

import com.github.valerie.wx.miniapp.model.Department;
import com.github.valerie.wx.miniapp.mapper.DepartmentMapper;
import com.github.valerie.wx.miniapp.service.DepartmentService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 部门信息(Department)表服务实现类
 *
 * @author makejava
 * @since 2020-03-23 10:41:48
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
        
    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取所有部门信息
     * */
    @Override
    public List<Department> getAllDepartments() {
        return this.departmentMapper.getAllDepartmentsByParentId(0);
    }

    /**
     * 查询下属部门
     *
     * @param pid parentId
     * @return 对象列表
     */
    @Override
    public List<Department> getAllDepartmentsByParentId(Integer pid) {
        return this.departmentMapper.getAllDepartmentsByParentId(pid);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Department selectById(Integer id) {
        return this.departmentMapper.selectById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Department> selectAllPaging(int offset, int limit) {
        return this.departmentMapper.selectAllPaging(offset, limit);
    }
    
    /**
     * 通过实体作为筛选条件查询
     *
     * @param department 实例对象
     * @return 对象列表
     */
    @Override
    public List<Department> selectAll(Department department) {
        return this.departmentMapper.selectAll(department);
    }
    
    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    @Override
    public List<Department> select(Map<String, Object> param) {
        return this.departmentMapper.select(param);
    }

    @Override
    public Long count(Map<String, Object> param) {
        return this.departmentMapper.count(param);
    }

    /**
     * 新增数据
     *
     * @param department 实例对象
     * @return 影响行数
     */
    @Override
    public int add(Department department) {
                        
        return this.departmentMapper.add(department);
    }

    /**
     * 修改数据
     *
     * @param department 实例对象
     * @return 影响行数
     */
    @Override
    public int update(Department department) {
                        
        return this.departmentMapper.update(department);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(Integer id) {
        return this.departmentMapper.deleteById(id);
    }

    @Override
    public void addDep(Department dep) {
        this.departmentMapper.addDep(dep);
    }

    @Override
    public void deleteDepById(Department dep) {
        this.departmentMapper.deleteDepById(dep);
    }
}
