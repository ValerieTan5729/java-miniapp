package com.github.valerie.wx.miniapp.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 用户的值班打卡记录(Record)实体类
 *
 * @author makejava
 * @since 2020-03-26 15:38:00
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String userName;

    /**
     * 值班表名称
     */
    @ApiModelProperty("值班表名称")
    private String dutyName;
    


}
