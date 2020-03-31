package com.github.valerie.wx.miniapp.service;

import com.github.valerie.wx.miniapp.model.Department;
import java.util.List;
import java.util.Map;

/**
 * 部门信息(Department)表服务接口
 *
 * @author makejava
 * @since 2020-03-23 10:41:48
 */
public interface DepartmentService {

    /**
     * 获取所有部门信息
     * */
    List<Department> getAllDepartments();

    /**
     * 查询下属部门
     *
     * @param pid parentId
     * @return 对象列表
     */
    List<Department> getAllDepartmentsByParentId(Integer pid);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Department selectById(Integer id);

    /**
     * 分页查询
     *
     * @param offset 查询起始页数
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Department> selectAllPaging(int offset, int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param department 实例对象
     * @return 对象列表
     */
    List<Department> selectAll(Department department);
    
    /**
     * 通过Map作为筛选条件查询
     *
     * @param param 查询条件
     * @return 对象列表
     */
    List<Department> select(Map<String, Object> param);

    Long count(Map<String, Object> param);

    /**
     * 新增数据
     *
     * @param department 实例对象
     * @return 影响行数
     */
    int add(Department department);

    /**
     * 修改数据
     *
     * @param department 实例对象
     * @return 影响行数
     */
    int update(Department department);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    void addDep(Department dep);

    void deleteDepById(Department dep);

}
