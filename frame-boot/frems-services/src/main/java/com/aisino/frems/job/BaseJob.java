package com.aisino.frems.job;


import com.aisino.frems.common.constant.QuartzErrorMsgConfig;
import com.aisino.frems.common.util.DateUtils;
import com.aisino.frems.common.util.oConvertUtils;
import com.aisino.frems.modules.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础定时器
 */
public abstract class BaseJob implements SystemCommonJob {

    private final static Logger logger = LoggerFactory.getLogger(BaseJob.class);

    @Resource
    private ISysConfigService sysConfigService;

    /**
     * 获取配置数据
     * @param configKeys 配置参数
     * @return 配置数据
     */
    protected Map<String, String> getConfigParams(String[] configKeys, String[] configMsgs) throws Exception {
        if (configKeys.length!=configMsgs.length) {
            String errorMsg = "Data configuration is abnormal, please adjust the number of parameters";
            logger.error(errorMsg);
            throw new Exception(errorMsg);
        }
        Map<String, String> configParam = new HashMap<>();
        for (int i=0; i<configKeys.length; i++) {
            String config = configKeys[i];
            String paramValue = sysConfigService.getParaValueByCode(config);
            if (oConvertUtils.isEmpty(paramValue)) {
                String errorMsg = String.format(QuartzErrorMsgConfig.configError.getValue(), config, configMsgs[i]);
                logger.error(errorMsg);
                throw new Exception(errorMsg);
            } else {
                configParam.put(config, paramValue);
            }
        }
        return configParam;
    }

    /**
     * 更新参数表数据
     * @param paraValue 更新数据
     * @param paraCode 更新数据对应key
     */
    protected void updateParaValueByCode(String paraValue, String paraCode){
        sysConfigService.updateParaValueByCode(paraValue, paraCode);
    }

    /**
     * 判断时间数据是否超过当前时间
     * 时间数据以配置的开始时间为准
     * @param time 格式yyyyMMdd
     * @return boolean
     * @throws ParseException 解析异常
     */
    protected boolean isExceedTime(String time) throws Exception {
        Date date = DateUtils.parseDate(time+"000000", "yyyyMMddHHmmss");
        if (date.getTime()> DateUtils.getMillis()) {
            String errorMsg = String.format("参数配置时间已超过当前时间，不再执行抽取任务: config time is %s, current time is %s",
                    time+"000000", DateUtils.formatDateTime());
            logger.error(errorMsg);
            return true;
        }
        return false;
    }

    /**
     * 增加天数计算
     * @param date 当前时间
     * @param days 增加天数
     * @return 返回时间
     * @throws ParseException 解析异常
     */
    protected String dateAddDays(String date, int days) throws ParseException {
        Date today = DateUtils.parseDate(date, "yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, days);// 今天+1天
        Date tomorrow = c.getTime();
        return DateUtils.date2Str(tomorrow, new SimpleDateFormat("yyyyMMdd"));
    }

    /**
     * 计算增加小时
     * @param cqrq 抽取日期
     * @param hour 增加小时
     * @return n小时后日期
     * @throws ParseException 解析异常
     */
    protected String dateAddHour(String cqrq, int hour) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
        if(cqrq.length()==8){
            cqrq+="00";
        }
        Date cqrqDate = dateFormat.parse(cqrq);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cqrqDate);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return dateFormat.format(calendar.getTime());
    }

}
