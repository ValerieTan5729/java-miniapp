package com.github.valerie.wx.miniapp.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * (MenuRole)实体类
 *
 * @author makejava
 * @since 2020-03-30 10:10:15
 */
@ApiModel
@Data
public class MenuRole {
        /**
    * ID
    */    
    @ApiModelProperty("ID")
    private Long id;
    
    /**
    * 菜单ID
    */    
    @ApiModelProperty("菜单ID")
    private Long menuId;
    
    /**
    * 角色ID
    */    
    @ApiModelProperty("角色ID")
    private Long roleId;
    


}