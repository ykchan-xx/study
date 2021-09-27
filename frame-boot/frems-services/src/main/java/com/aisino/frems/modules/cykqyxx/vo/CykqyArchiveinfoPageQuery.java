package com.aisino.frems.modules.cykqyxx.vo;

import lombok.Data;
import org.asframework.data.mybatis.model.BasePageQuery;
import com.aisino.frems.modules.cykqyxx.entity.CykqyArchiveinfo;

import java.util.Date;

/**
 * cyk企业信息分页查询对象类
 *
 * @author create by cyk
 * @date 2021-09-24
 */
@Data
public class CykqyArchiveinfoPageQuery extends BasePageQuery<CykqyArchiveinfo> {

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
