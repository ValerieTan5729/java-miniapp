package com.github.valerie.wx.miniapp.model;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 值班表信息(Duty)实体类
 *
 * @author makejava
 * @since 2020-03-18 15:23:40
 */
@ApiModel
@Data
public class Duty {
    /**
    * 值班表ID
    */    
    @ApiModelProperty("值班表ID")
    private Long id;
    
    /**
    * 值班表名称
    */    
    @ApiModelProperty("值班表名称")
    private String name;
    
    /**
    * 开始时间
    */    
    @ApiModelProperty("开始时间")
    private Date beginDate;
    
    /**
    * 结束时间
    */    
    @ApiModelProperty("结束时间")
    private Date endDate;
    
    /**
    * 备注
    */    
    @ApiModelProperty("备注")
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
    


}
