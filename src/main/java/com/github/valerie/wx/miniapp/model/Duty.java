package com.github.valerie.wx.miniapp.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 值班表信息(Duty)实体类
 *
 * @author makejava
 * @since 2020-03-23 11:10:30
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginDate;
    
    /**
    * 结束时间
    */    
    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endDate;
    
    /**
    * 备注
    */    
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 当前当值的值班表(0未生效的/1当前的)
     */
    @ApiModelProperty("当前当值的值班表(0未生效的/1当前的)")
    private Integer active;
    
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

}
