package com.aisino.frems.modules.ryxx.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 企业人员附件信息管理 Entity实体类
 *
 * @author create by yangshunfei
 * @date 2021-06-23
 */
@Data
@TableName(value = "qy_ryfjxx")
public class Ryfjxx extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 企业人员主键ID */
	@TableField(value = "QY_RYID")
	private Long qyRyid;
	/** 企业人员头像 */
	@TableField(value = "avatar")
	private String avatar;

}
