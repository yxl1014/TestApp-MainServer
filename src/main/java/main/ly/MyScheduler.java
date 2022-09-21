package main.ly;


import main.ly.Job.StorageJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class MyScheduler {
    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    public MyScheduler() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        storageJob(scheduler);
        serializeJob(scheduler);
    }

    public void storageJob(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail=newJob(StorageJob.class)
                .withDescription("存入数据库")
                .withIdentity("storageDB","x")
                .build();
        CronTrigger cronTrigger=newTrigger()
                .withDescription("存储触发器")//描述
                .withIdentity("taskTrigger", "z")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/10 * * * ?"))
                .build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    public void serializeJob(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail=newJob(StorageJob.class)
                .withDescription("序列化数据")
                .withIdentity("serialize","x")
                .build();
        CronTrigger cronTrigger=newTrigger()
                .withDescription("序列化触发器")//描述
                .withIdentity("taskTrigger1", "z")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/10 * * * ?"))
                .build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }
}
