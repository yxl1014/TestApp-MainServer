package main.yxl.publicContext.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import main.pto.TestProto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author main.yxl
 * @date: 2022/9/19 上午11:15
 */

@Configuration
public class PublicContextBean {


    @Bean
    public Map<Integer, TestProto.Task> getTaskMap() {
        return new HashMap<>();
    }
}
