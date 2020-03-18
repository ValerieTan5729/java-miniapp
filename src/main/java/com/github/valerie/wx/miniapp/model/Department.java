package com.github.valerie.wx.miniapp.model;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 部门信息(Department)实体类
 *
 * @author makejava
 * @since 2020-03-18 15:23:20
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
    * 状态(0有效/1无效)
    */    
    @ApiModelProperty("状态(0有效/1无效)")
    private Integer status;
    
    /**
    * 创建时间
    */    
    @ApiModelProperty("创建时间")
    private Date creatTime;
    
    /**
    * 更新时间
    */    
    @ApiModelProperty("更新时间")
    private Date updateTime;
    
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
    


}
