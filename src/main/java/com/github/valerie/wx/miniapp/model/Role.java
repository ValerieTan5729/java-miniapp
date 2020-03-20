package com.github.valerie.wx.miniapp.model;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * (Role)实体类
 *
 * @author makejava
 * @since 2020-03-19 09:06:27
 */
@ApiModel
@Data
public class Role {
    /**
    * 角色ID
    */    
    @ApiModelProperty("角色ID")
    private Long id;
    
    /**
    * 角色code
    */    
    @ApiModelProperty("角色code")
    private String name;
    
    /**
    * 角色名称
    */    
    @ApiModelProperty("角色名称")
    private String namezh;
    
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
