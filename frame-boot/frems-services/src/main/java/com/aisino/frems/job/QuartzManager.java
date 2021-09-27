package com.aisino.frems.job;

import com.aisino.frems.common.util.SpringContextUtils;
import com.aisino.frems.modules.system.entity.SysQuartzJob;
import com.aisino.frems.modules.system.service.ISysQuartzJobService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 定时任务总控制类(从数据库中读取配置，并执行启动、重设周期、关停等操作)
 *
 * @author huangjinfang on 2017/8/9.
 */
@Service("quartzManager")
public class QuartzManager extends QuartzJobBean implements BeanFactoryAware {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(QuartzManager.class.getName());
    @Resource
    private ISysQuartzJobService sysQuartzJobService;

    @Resource
    @Qualifier("schedulerFactoryBean")
    private Scheduler scheduler;

    /**
     * 定时从system_quartz_config配置表中查询定时任务的配置
     */
    protected void reScheduleJob() {
        List<SysQuartzJob> quartzList = sysQuartzJobService.list(new LambdaQueryWrapper<SysQuartzJob>().eq(SysQuartzJob::getDelFlag, 0));
        if (quartzList.size() > 0) {
            for (SysQuartzJob sysQuartzJob : quartzList) {
                configQuatrz(sysQuartzJob);
            }
        }
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    }

    /**
     * bean获取或动态注入
     *
     * @param sysQuartzJob 定时任务基本信息
     * @return result 成功为true，失败为false
     */
    private boolean configQuatrz(SysQuartzJob sysQuartzJob) {
        boolean result = false;
        try {
            // 运行时可通过动态注入的scheduler得到trigger
            TriggerKey triggerKey = TriggerKey.triggerKey(sysQuartzJob.getJobClassName(), Scheduler.DEFAULT_GROUP);
            CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
            // 如果计划任务已存在则调用修改方法
            if (trigger != null) {
                change(sysQuartzJob, trigger);
            } else {
                if (1 == sysQuartzJob.getStatus()) {
                    this.createCronTriggerBean(sysQuartzJob);
                }
            }
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 改变定时任务周期
     *
     * @throws Exception
     */
    private void change(SysQuartzJob sysQuartzJob, CronTriggerImpl trigger) throws Exception {
        // 如果任务为可用
        if (1 == sysQuartzJob.getStatus()) {
            // 判断从DB中取得的任务时间和现在的quartz线程中的任务时间是否相等
            // 如果相等，则表示用户并没有重新设定数据库中的任务时间，这种情况不需要重新rescheduleJob
            if (!trigger.getCronExpression().equalsIgnoreCase(sysQuartzJob.getCronExpression())) {
                LOGGER.info(sysQuartzJob.getJobClassName() + "任务时间发生了变化，将更新周期!");
                trigger.setCronExpression(sysQuartzJob.getCronExpression());
                TriggerKey triggerKey = TriggerKey.triggerKey(sysQuartzJob.getJobClassName(), Scheduler.DEFAULT_GROUP);
                scheduler.rescheduleJob(triggerKey, trigger);
                LOGGER.info(new Date() + ": 更新" + sysQuartzJob.getJobClassName() + "计划任务" + sysQuartzJob.getCronExpression());
            }
        } else {
            // 不可用
            delete(sysQuartzJob, trigger);
        }

    }

    /**
     * 创建/修改计划任务
     *
     * @param sysQuartzJob 计划任务配置对象
     * @throws Exception
     */
    private void createCronTriggerBean(SysQuartzJob sysQuartzJob) throws Exception {
        // 新建一个基于Spring的管理Job类
        MethodInvokingJobDetailFactoryBean mjdfb = new MethodInvokingJobDetailFactoryBean();
        // 设置job名称
        mjdfb.setName(sysQuartzJob.getJobClassName());
        // 如果定义的任务类为Spring的定义的Bean则调用 getBean方法
        // 设置任务类
        mjdfb.setTargetObject(SpringContextUtils.getBean(sysQuartzJob.getJobClassName()));
        // 设置任务方法
        mjdfb.setTargetMethod("quartzInit");
//        mjdfb.setArguments(sysQuartzJob.getParameter());
        //设置是否并发启动任务
        mjdfb.setConcurrent(false);
        //将管理Job类提交到计划管理类
        mjdfb.afterPropertiesSet();
//        Class jobClass = mjdfb.getTargetObject().getClass();
        // 将Spring的管理Job类转为Quartz管理Job类
        JobDetail jobDetail = mjdfb.getObject();
        // 新一个基于Spring的时间类
        CronTriggerFactoryBean c = new CronTriggerFactoryBean();
        c.setName(sysQuartzJob.getJobClassName());
        // 设置时间表达式
        c.setCronExpression(sysQuartzJob.getCronExpression());
        c.setGroup(Scheduler.DEFAULT_GROUP);
        c.setJobDetail(jobDetail);
        c.afterPropertiesSet();
        // 设置管理类
        scheduler.scheduleJob(jobDetail, c.getObject());
        TriggerKey triggerKey = TriggerKey.triggerKey(sysQuartzJob.getJobClassName(), Scheduler.DEFAULT_GROUP);
        // 刷新管理类
        scheduler.rescheduleJob(triggerKey, c.getObject());
        LOGGER.info(new Date() + ":新建" + sysQuartzJob.getJobClassName() + "计划任务");
    }

    private void delete(SysQuartzJob sysQuartzJob, CronTriggerImpl trigger) throws Exception {
        // 停止触发器
        scheduler.pauseTrigger(trigger.getKey());
        // 移除触发器
        scheduler.unscheduleJob(trigger.getKey());
        // 删除任务
        scheduler.deleteJob(trigger.getJobKey());
        LOGGER.info(new Date() + ": 删除" + sysQuartzJob.getJobClassName() + "计划任务");
    }
}
