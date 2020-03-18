package com.github.valerie.wx.miniapp.model;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户的值班打卡记录(Record)实体类
 *
 * @author makejava
 * @since 2020-03-18 15:26:13
 */
@ApiModel
@Data
public class Record {
    /**
    * 打卡记录ID
    */    
    @ApiModelProperty("打卡记录ID")
    private Long id;
    
    /**
    * 用户ID
    */    
    @ApiModelProperty("用户ID")
    private Long userId;
    
    /**
    * 值班表ID
    */    
    @ApiModelProperty("值班表ID")
    private Long dutyId;
    
    /**
    * 打卡日期
    */    
    @ApiModelProperty("打卡日期")
    private Date date;
    
    /**
    * 打卡地点
    */    
    @ApiModelProperty("打卡地点")
    private String place;
    
    /**
    * 二维码照片path
    */    
    @ApiModelProperty("二维码照片path")
    private String imgPath;
    
    /**
    * 备注
    */    
    @ApiModelProperty("备注")
    private String remark;
    
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
