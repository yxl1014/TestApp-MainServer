package main.yxl.publicContext.config;

import main.yxl.publicContext.config.contextBean.TaskMapUtil;
import main.yxl.publicContext.config.contextBean.UserMapUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pto.TestProto;

import java.util.HashMap;

/**
 * @author main.yxl
 * @date: 2022/9/19 上午11:15
 */

@Configuration
public class PublicContextBean {


    @Bean
    public TaskMapUtil getTaskMap() {
        return new TaskMapUtil();
    }

    @Bean
    public UserMapUtil getUserMap() {
        return new UserMapUtil();
    }

    @Bean(name = "conductMap")
    public HashMap<Integer, TestProto.TaskConduct.Builder> getConductMap() {
        return new HashMap<>();
    }
}
