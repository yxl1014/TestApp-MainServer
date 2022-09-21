package main.ly;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class quartzUtils {

    @Autowired
    Scheduler scheduler;

    private static final String JOB_GROUP_NAME="JOB_GROUP_SYSTEM";
    private static final String TRIGGER_GROUP_NAME="TRIGGER_GROUP_SYSTEM";



    public void start() throws SchedulerException {
        scheduler.start();
    }

    public void addJob(String jobName, @SuppressWarnings("rawtypes") Class cls, Object params) {
        try {
            JobKey jobKey=new JobKey(jobName,JOB_GROUP_NAME);
            JobDataMap jobDataMap=new JobDataMap();
            jobDataMap.put("params",params);
            JobDetail jobDetail= JobBuilder.newJob(cls).withIdentity(jobKey).setJobData(jobDataMap).build();
            TriggerKey triggerKey=new TriggerKey(jobName,TRIGGER_GROUP_NAME);
            CronScheduleBuilder scheduleBuilder=CronScheduleBuilder.cronSchedule("* * * * * ?");
            CronTrigger trigger=TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static void removeJob(Scheduler sched, String jobName) {
        try {
            TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
            sched.deleteJob(jobKey);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }
}
