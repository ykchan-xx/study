package org.asframework.generator;

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
        info.setTableName("qy_yj");
        info.setTablePrefix("");
        info.setBasePackage("com.aisino.frems.modules.yjxx");
        info.setControllerPackage("com.aisino.frems.modules.yjxx.controller");
        info.setVoPackage("com.aisino.frems.modules.yjxx.vo");
        info.setModuleName("");
        info.setPageUrl("qygl/yjxx");
        info.setAuthor("create by luozheng");
        info.setComments("预警信息");
        info.setPageFilterFields("lrr,lrsj,zhxgr,zhxgsj");
        generator.configDb(DBType.MYSQL, "jdbc:mysql://192.168.7.16:3307/vue-study?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC", "study", "study0713")
                .configGen(info)
                .configQueryConditions("qybh","qymc", "ywxm", "xb", "csrq","gjdq")
                .configTemplate(TplTypeEnum.MY_BATIS)
                .generateCode();
    }
}
