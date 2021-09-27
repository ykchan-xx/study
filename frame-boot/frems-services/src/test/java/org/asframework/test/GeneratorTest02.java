package org.asframework.test;

import org.asframework.generator.AsCodeGenerator;
import org.asframework.generator.config.GenConfigInfo;
import org.asframework.generator.enums.DBType;
import org.asframework.generator.enums.TplTypeEnum;
import org.junit.Test;

/**
 * Created by wenzhaoming on 2019-8-8.
 */
public class GeneratorTest02 {

    @Test
    public void test() {
        AsCodeGenerator generator = new AsCodeGenerator();
        GenConfigInfo info = new GenConfigInfo();
        info.setTableName("hdl_ygxx");
        info.setTablePrefix("");
        info.setBasePackage("com.aisino.frems.modules.hdl_ygxx");
        info.setControllerPackage("com.aisino.frems.modules.hdl_ygxx.controller");
        info.setVoPackage("com.aisino.frems.modules.hdl_ygxx.vo");
        info.setModuleName("");
        info.setPageUrl("qygl/hdl_ygxx");
        info.setAuthor("create by huangdanlei");
        info.setComments("员工信息");
        info.setPageFilterFields("lrr,lrsj,zhxgr,zhxgsj");
        generator.configDb(DBType.MYSQL, "jdbc:mysql://192.168.7.16:3307/vue-study?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC", "study", "study0713")
                .configGen(info)
                .configQueryConditions("ygname","ygsex", "ygqy", "ygbirth","ygsfzid", "ygyx","ygqyid")
                .configTemplate(TplTypeEnum.MY_BATIS)
                .generateCode();
    }
}
