package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 定时任务配置 Entity实体类
 *
 * @author hxq
 * @date 2020-04-23
 */
@Data
@TableName(value = "sys_quartz_job")
public class SysQuartzJob extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    @TableField(value = "id")
    private String id;
    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * 删除状态
     */
    @TableField(value = "del_flag")
    private Integer delFlag;
    /**
     * 修改人
     */
    @TableField(value = "update_by")
    private String updateBy;
    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * 任务类名
     */
    @TableField(value = "job_class_name")
    private String jobClassName;
    /**
     * cron表达式
     */
    @TableField(value = "cron_expression")
    private String cronExpression;
    /**
     * 参数
     */
    @TableField(value = "parameter")
    private String parameter;
    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;
    /**
     * 状态 0正常 -1停止
     */
    @TableField(value = "status")
    private Integer status;
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
