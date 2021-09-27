package com.aisino.frems.modules.ryxx.vo;

import lombok.Data;
import org.asframework.data.mybatis.model.BasePageQuery;
import com.aisino.frems.modules.ryxx.entity.Ryxx;

import java.util.Date;

/**
 * 企业人员管理分页查询对象类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Data
public class RyxxPageQuery extends BasePageQuery<Ryxx> {

    /** 中文姓名 */
    private String zwxm;
    /** 英文姓名 */
    private String ywxm;
    /** 证件号码 */
    private String zjhm;
    /** 单位名称 */
    private String dwmc;

}
