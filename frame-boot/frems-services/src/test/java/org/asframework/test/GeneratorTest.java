package org.asframework.test;

import org.asframework.generator.AsCodeGenerator;
import org.asframework.generator.config.GenConfigInfo;
import org.asframework.generator.enums.DBType;
import org.asframework.generator.enums.TplTypeEnum;
import org.junit.Test;

/**
 * Created by wenzhaoming on 2019-8-8.
 */
public class GeneratorTest {

    @Test
    public void test() {
        AsCodeGenerator generator = new AsCodeGenerator();
        GenConfigInfo info = new GenConfigInfo();
        info.setTableName("hdl_qyxx");
        info.setTablePrefix("");
        info.setBasePackage("com.aisino.frems.modules.hdl_qyxx");
        info.setControllerPackage("com.aisino.frems.modules.hdl_qyxx.controller");
        info.setVoPackage("com.aisino.frems.modules.hdl_qyxx.vo");
        info.setModuleName("");
        info.setPageUrl("qygl/hdl_qyxx");
        info.setAuthor("create by huangdanlei");
        info.setComments("企业信息");
        info.setPageFilterFields("lrr,lrsj,zhxgr,zhxgsj");
        generator.configDb(DBType.MYSQL, "jdbc:mysql://192.168.7.16:3307/vue-study?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC", "study", "study0713")
                .configGen(info)
                .configQueryConditions("qybm","qymc", "qydz", "fr","qylb", "qyf","qy_starttime","qy_endtime")
                .configTemplate(TplTypeEnum.MY_BATIS)
                .generateCode();
    }
}
