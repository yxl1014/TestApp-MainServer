package main.kafka.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yxl
 * @date: 2022/10/5 下午4:02
 */

@Component
@Data
public class KafkaPropertiesBean {
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrap;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private String autoCommit;

    @Value("${spring.kafka.consumer.auto-commit-interval}")
    private String interval;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String key_deserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String value_deserializer;
}
