package main.ly.config;

import main.ly.config.LruBean.LruUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pto.TestProto;

@Configuration
public class LruConfig {

    @Value("${lru.maxsize}")
    private int capacity;

    @Bean
    public LruUtil<TestProto.Task.Builder> getLruList() {
        return new LruUtil<>(capacity);
    }
}
