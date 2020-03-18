package com.github.valerie.wx.miniapp.model;

import java.util.Collection;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * 用户信息(User)实体类
 *
 * @author makejava
 * @since 2020-03-17 14:42:52
 */
@ApiModel
@Data
public class User implements UserDetails {
    /**
    * 用户ID
    */    
    @ApiModelProperty("用户ID")
    private Long id;
    
    /**
    * 用户名称
    */    
    @ApiModelProperty("用户名称")
    private String name;
    
    /**
    * 用户手机号码
    */    
    @ApiModelProperty("用户手机号码")
    private String phone;
    
    /**
    * 用户密码
    */    
    @ApiModelProperty("用户密码")
    private String password;
    
    /**
    * 用户权限
    */    
    @ApiModelProperty("用户权限")
    private Integer roleId;
    
    /**
    * 用户的微信openId
    */    
    @ApiModelProperty("用户的微信openId")
    private String openId;
    
    /**
    * 用户值班级别ID
    */    
    @ApiModelProperty("用户值班级别ID")
    private Integer dutyId;
    
    /**
    * 用户部门ID
    */    
    @ApiModelProperty("用户部门ID")
    private Integer depId;
    
    /**
    * 用户备注
    */    
    @ApiModelProperty("用户备注")
    private String remark;
    
    /**
    * 用户状态(0有效/1无效)
    */    
    @ApiModelProperty("用户状态(0有效/1无效)")
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status==0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status==0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status==0;
    }

    @Override
    public boolean isEnabled() {
        return status==0;
    }
}
