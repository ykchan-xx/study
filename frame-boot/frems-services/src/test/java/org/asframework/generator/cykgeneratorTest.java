package org.asframework.generator;

import org.asframework.generator.config.GenConfigInfo;
import org.asframework.generator.enums.DBType;
import org.asframework.generator.enums.TplTypeEnum;
import org.junit.Test;

/**
 * Created by wenzhaoming on 2019-8-8.
 */
public class cykgeneratorTest {

    @Test
    public void test() {
        AsCodeGenerator generator = new AsCodeGenerator();
        GenConfigInfo info = new GenConfigInfo();
        info.setTableName("cykqy_rx");
        info.setTablePrefix("");
        info.setBasePackage("com.aisino.frems.modules.cykryxx");
        info.setControllerPackage("com.aisino.frems.modules.cykryxx.controller");
        info.setVoPackage("com.aisino.frems.modules.cykryxx.vo");
        info.setModuleName("");
        info.setPageUrl("qygl/cykryxx");
        info.setAuthor("create by cyk");
        info.setComments("cyk人员信息");
        info.setPageFilterFields("LRR,LRSJ,ZHXGR,ZHXGSJ");
//        generator.configDb(DBType.MYSQL, "jdbc:mysql://localhost:3306/frems?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&serverTimezone=GMT%2B8&useSSL=false", "root", "dudummm123")
        generator.configDb(DBType.MYSQL, "jdbc:mysql://192.168.7.16:3307/vue-study?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC", "study", "study0713")
                .configGen(info)
                .configQueryConditions("ZWXM","YWXM", "ZJHM", "DWMC")
                .configTemplate(TplTypeEnum.MY_BATIS)
                .generateCode();
    }
}
