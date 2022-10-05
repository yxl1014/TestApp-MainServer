package main.kafka.config;

import main.kafka.common.KafkaConsumerConnectPoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author yxl
 * @date: 2022/10/5 下午3:56
 */

@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaConsumerBean bean;

    @Bean(name = "kafkaProperties")
    public Properties getKafkaConsumerProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bean.getBootstrap());
        //每个消费者分配独立的组号
        props.put("group.id", bean.getGroupId());

        //如果value合法，则自动提交偏移量
        props.put("enable.auto.commit", bean.getAutoCommit());

        //设置多久一次更新被消费消息的偏移量
        props.put("auto.commit.interval.ms", bean.getInterval());

        //设置会话响应的时间，超过这个时间kafka可以选择放弃消费或者消费下一条消息
        props.put("session.timeout.ms", "30000");

        props.put("key.deserializer", bean.getKey_deserializer());
        props.put("value.deserializer", bean.getValue_deserializer());
        return props;
    }
}
