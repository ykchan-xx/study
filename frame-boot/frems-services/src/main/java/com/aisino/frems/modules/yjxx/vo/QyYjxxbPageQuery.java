package com.aisino.frems.modules.yjxx.vo;

import com.aisino.frems.modules.yjxx.entity.QyYjxxb;
import lombok.Data;
import org.asframework.data.mybatis.model.BasePageQuery;

/**
 * 预警信息分页查询对象类
 *
 * @author create by luozheng
 * @date 2021-07-02
 */
@Data
public class QyYjxxbPageQuery extends BasePageQuery<QyYjxxb> {

    /** 企业编号 */
    private String qybh;
    /** 企业名称 */
    private String qymc;
    /** 英文姓名 */
    private String ywxm;
    /** 性别 */
    private String sex;
    /** 出生日期 */
    private String csrq;
    /** 国家地区 */
    private String gjdq;

}
