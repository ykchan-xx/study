package com.aisino.frems.modules.qygl.vo;

import lombok.Data;
import org.asframework.data.mybatis.model.BasePageQuery;
import com.aisino.frems.modules.qygl.entity.Archiveinfo;

import java.util.Date;

/**
 * 开票回款分页查询对象类
 *
 * @author create by caochundi
 * @date 2021-06-22
 */
@Data
public class ArchiveinfoPageQuery extends BasePageQuery<Archiveinfo> {

    /** 企业类别
 */
    private String qylb;
    /** 企业名称
 */
    private String qymc;
    /** 企业编码
 */
    private String qybm;
    /** 法人
 */
    private String fr;
    /** 联系电话
 */
    private String lxdh;

}
