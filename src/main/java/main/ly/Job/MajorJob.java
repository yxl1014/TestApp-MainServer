package main.ly.Job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MajorJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("-------触发器开始执行--------");
        System.out.println("开始序列化.....");
        System.out.println("开始落盘");
    }
}
