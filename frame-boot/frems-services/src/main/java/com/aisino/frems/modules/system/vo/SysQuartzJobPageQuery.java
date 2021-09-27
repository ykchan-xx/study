package com.aisino.frems.modules.system.vo;

import lombok.Data;
import org.asframework.data.mybatis.model.BasePageQuery;
import com.aisino.frems.modules.system.entity.SysQuartzJob;

/**
 * 定时任务配置分页查询对象类
 *
 * @author hxq
 * @date 2020-04-23
 */
@Data
public class SysQuartzJobPageQuery extends BasePageQuery<SysQuartzJob> {


}
