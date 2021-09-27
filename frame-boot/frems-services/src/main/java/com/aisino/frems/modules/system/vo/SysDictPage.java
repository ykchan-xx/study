package com.aisino.frems.modules.system.vo;

import lombok.Data;
import com.aisino.frems.modules.system.entity.SysDictItem;

import java.util.List;

@Data
public class SysDictPage {

    /**
     * 主键
     */
    private String id;
    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 删除状态
     */
    private Integer delFlag;
    /**
     * 描述
     */
    private String description;
    /** 租户编号 */
    private String zhbh;
    /** 企业编号 */
    private String qybh;
    /** 部门编号 */
    private String bmbh;

    private List<SysDictItem> sysDictItemList;

}
