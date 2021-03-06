package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 填值规则
 * @Author: jeecg-boot
 * @Date: 2019-11-07
 * @Version: V1.0
 */
@Data
@TableName("sys_fill_rule")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "sys_fill_rule对象", description = "填值规则")
public class SysFillRule {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键ID")
    private java.lang.String id;
    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称")
    private java.lang.String ruleName;
    /**
     * 规则Code
     */
    @ApiModelProperty(value = "规则Code")
    private java.lang.String ruleCode;
    /**
     * 规则实现类
     */
    @ApiModelProperty(value = "规则实现类")
    private java.lang.String ruleClass;
    /**
     * 规则参数
     */
    @ApiModelProperty(value = "规则参数")
    private java.lang.String ruleParams;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private java.lang.String updateBy;
    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
    /** 租户编号 */
    @TableField(value = "zhbh")
    private String zhbh;
    /** 企业编号 */
    @TableField(value = "qybh")
    private String qybh;
    /** 部门编号 */
    @TableField(value = "bmbh")
    private String bmbh;
}
