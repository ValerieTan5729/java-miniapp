package com.github.valerie.wx.miniapp.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户与权限之间的关系(UserRole)实体类
 *
 * @author makejava
 * @since 2020-03-30 10:09:22
 */
@ApiModel
@Data
public class UserRole {
    /**
    * ID
    */    
    @ApiModelProperty("ID")
    private Long id;
    
    /**
    * 用户ID
    */    
    @ApiModelProperty("用户ID")
    private Long userId;
    
    /**
    * 角色ID
    */    
    @ApiModelProperty("角色ID")
    private Long roleId;
    


}
