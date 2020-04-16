package com.github.valerie.wx.miniapp.model;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 部门信息(Department)实体类
 *
 * @author makejava
 * @since 2020-03-23 10:41:48
 */
@ApiModel
@Data
public class Department {
    /**
    * 部门ID
    */    
    @ApiModelProperty("部门ID")
    private Integer id;
    
    /**
    * 部门名称
    */    
    @ApiModelProperty("部门名称")
    private String name;
    
    /**
    * 上级部门ID
    */    
    @ApiModelProperty("上级部门ID")
    private Integer parentId;

    /**
     * 是否拥有子部门
     * */
    @ApiModelProperty("是否拥有子部门")
    private boolean isParent;

    /**
     * 下级部门
     */
    @ApiModelProperty("下级部门")
    private List<Department> children;
    
    /**
    * 状态(0有效/1无效)
    */    
    @ApiModelProperty("状态(0有效/1无效)")
    private Integer status;

    /**
    * 备用字段1
    */    
    @ApiModelProperty("备用字段1")
    private String tempA;
    
    /**
    * 备用字段2
    */    
    @ApiModelProperty("备用字段2")
    private String tempB;

    private Integer result;
}
