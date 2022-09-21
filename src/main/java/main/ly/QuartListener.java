package main.ly;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class QuartListener implements ApplicationListener<ContextRefreshedEvent> {
        @Autowired
        private quartzUtils quartzUtils;

        @Override
        public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
            try {
                quartzUtils.start();
                System.out.println("*******quartz调度器启动*******");
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }

