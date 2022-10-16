package main.ly.config;

import main.ly.config.LruBean.LruUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pto.TestProto;

@Configuration
public class LruConfig {

    @Bean
    public LruUtil<TestProto.Task.Builder> getLruList(int capacity){
        return new LruUtil<>(capacity);
    }
}
