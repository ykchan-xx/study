package com.aisino.frems.modules.hdl_ygxx.vo;

import lombok.Data;
import org.asframework.data.mybatis.model.BasePageQuery;
import com.aisino.frems.modules.hdl_ygxx.entity.HdlYgxx;

import java.util.Date;

/**
 * 员工信息分页查询对象类
 *
 * @author create by huangdanlei
 * @date 2021-07-23
 */
@Data
public class HdlYgxxPageQuery extends BasePageQuery<HdlYgxx> {

    /** 员工名字 */
    private String ygname;
    /** 员工性别 */
    private String ygsex;
    /** 员工企业 */
    private String ygqy;
    /** 员工出生日期 */
    private String ygbirth;
    /** 员工身份证号 */
    private String ygsfzid;
    /** 员工邮箱 */
    private String ygyx;
    /** 员工号 */
    private String ygqyid;

}
