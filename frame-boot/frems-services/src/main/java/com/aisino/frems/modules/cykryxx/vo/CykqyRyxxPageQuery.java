package com.aisino.frems.modules.cykryxx.vo;

import lombok.Data;
import org.asframework.data.mybatis.model.BasePageQuery;
import com.aisino.frems.modules.cykryxx.entity.CykqyRyxx;

import java.util.Date;

/**
 * cyk人员信息分页查询对象类
 *
 * @author create by cyk
 * @date 2021-09-27
 */
@Data
public class CykqyRyxxPageQuery extends BasePageQuery<CykqyRyxx> {
    /** 中文姓名 */
    private String zwxm;
    /** 英文姓名 */
    private String ywxm;
    /** 证件号码 */
    private String zjhm;
    /** 单位名称 */
    private String dwmc;


}
