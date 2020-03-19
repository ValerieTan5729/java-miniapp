package com.github.valerie.wx.miniapp.model;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * (Menu)实体类
 *
 * @author makejava
 * @since 2020-03-19 09:12:23
 */
@ApiModel
@Data
public class Menu {
    /**
    * 菜单ID
    */    
    @ApiModelProperty("菜单ID")
    private Long id;
    
    /**
    * 菜单名称
    */    
    @ApiModelProperty("菜单名称")
    private String name;
    
    /**
    * 后端API访问路径
    */    
    @ApiModelProperty("后端API访问路径")
    private String url;
    
    /**
    * 前端页面路径
    */    
    @ApiModelProperty("前端页面路径")
    private String path;
    
    /**
    * 是否需要登录之后才能访问(0需要/1不需要)
    */    
    @ApiModelProperty("是否需要登录之后才能访问(0需要/1不需要)")
    private Integer auth;
    
    /**
    * 上一级菜单ID
    */    
    @ApiModelProperty("上一级菜单ID")
    private Long parentid;
    
    /**
    * 状态(0有效/1无效)
    */    
    @ApiModelProperty("状态(0有效/1无效)")
    private Integer status;
    
    /**
    * 修改记录
    */    
    @ApiModelProperty("修改记录")
    private String note;
    
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

    /**
     * 菜单对应的权限
     * */
    @ApiModelProperty("菜单对应的权限")
    private List<Role> roles;
    


}
