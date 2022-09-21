package main.ly;

import jdk.nashorn.internal.runtime.StoredScript;
import main.ly.Job.SchedulerBean;
import main.ly.Job.SerializeJob;
import main.ly.Job.StorageJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.quartz.JobBuilder.newJob;

public class TaskExec {

//    @Test
//    void xxx() throws SchedulerException {
//        SchedulerFactory factory=new StdSchedulerFactory();
//        Scheduler scheduler=factory.getScheduler();
//
//        JobDetail jobDetail=newJob(StorageJob.class)
//                .withDescription("存入数据库")
//                .withIdentity("storageDB","x").build();
//
//        JobDetail jobDetail2=newJob(SerializeJob.class)
//                .withDescription("序列化")
//                .withIdentity("serialize","x").build();
//
//        CronTrigger cronTrigger=TriggerBuilder.newTrigger()
//                .withDescription("任务触发器")//描述
//                .withIdentity("taskTrigger", "z")
//                .startNow()
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/10 * * * ?"))
//                .build();
//
//        scheduler.scheduleJob(jobDetail,cronTrigger);
//        scheduler.start();
//    }

    @Autowired
    private SchedulerBean scheduler;

    public void xxx(){

    }
}
