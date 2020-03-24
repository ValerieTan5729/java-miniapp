package com.github.valerie.wx.miniapp.model;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 系统的数字字典(Dictory)实体类
 *
 * @author makejava
 * @since 2020-03-24 08:57:06
 */
@ApiModel
@Data
public class Dictory {
    /**
    * 数据字典ID
    */    
    @ApiModelProperty("数据字典ID")
    private Long id;
    
    /**
    * 名称
    */    
    @ApiModelProperty("名称")
    private String name;
    
    /**
    * 0为开始父节点
    */    
    @ApiModelProperty("0为开始父节点")
    private Long parentId;
    
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
     * 子节点
     * */
    @ApiModelProperty("子节点")
    private List<Dictory> children;
    
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
