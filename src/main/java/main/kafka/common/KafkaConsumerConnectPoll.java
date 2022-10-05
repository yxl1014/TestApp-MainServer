package main.kafka.common;
import main.kafka.runnable.KafkaConsumerRunner;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * kafkaTopic连接池
 * @author yxl
 * @date: 2022/10/5 下午5:24
 */

@Component
public class KafkaConsumerConnectPoll {

    private final static Logger logger = LogUtil.getLogger(KafkaConsumerConnectPoll.class);

    @Qualifier("kafkaProperties")
    private Properties properties;

    /**
     * String               topicName
     * KafkaConsumerRunner  kafka异步消费对象
     */
    public final Map<String, KafkaConsumerRunner> consumers = new HashMap<>();


    /**
     * 关闭这条队列的监听
     *
     * @param topicName topic名
     */
    public void shutdown(String topicName) {
        if (consumers.containsKey(topicName)) {
            consumers.get(topicName).shutdown();
            logger.info(LogUtil.makeKafkaLog(LogMsg.KAFKA, OptionDetails.KAFKA_CONSUMER_TOPIC_CLOSE_OK, topicName));
            this.consumers.remove(topicName);
        } else {
            logger.info(LogUtil.makeKafkaLog(LogMsg.KAFKA, OptionDetails.KAFKA_CONSUMER_TOPIC_CLOSE_NO_TOPIC, topicName));
        }
    }


    /**
     * 获取consumerRunner
     *
     * @param topicName topic名
     * @return 返回一个runner
     */
    public KafkaConsumerRunner getConsumer(String topicName) {
        return consumers.get(topicName);
    }

    /**
     * 初始化并开始消费这条队列
     *
     * @param topicName topic名
     */
    public void initKafkaTopic(String topicName) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        KafkaConsumerRunner kafkaConsumerRunner = new KafkaConsumerRunner(topicName, consumer);
        KafkaConsumerRunner old = this.consumers.get(topicName);

        //若有旧的topic且他还没有关闭
        if (old != null && !old.getClosed().get()) {
            logger.info(LogUtil.makeKafkaLog(LogMsg.KAFKA, OptionDetails.KAFKA_CONSUMER_TOPIC_START_EXIST_OK, topicName));
            old.shutdown();
        }
        this.consumers.put(topicName, kafkaConsumerRunner);
        Thread thread = new Thread(kafkaConsumerRunner);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.info(LogUtil.makeKafkaLog(LogMsg.KAFKA, OptionDetails.KAFKA_CONSUMER_TOPIC_START_ERROR, topicName));
            logger.warn(e);
        }
        logger.info(LogUtil.makeKafkaLog(LogMsg.KAFKA, OptionDetails.KAFKA_CONSUMER_TOPIC_START_OK, topicName));
    }

    /**
     * 获取这个监听队列,若为null则初始化并开始一个
     * @param topicName topic名
     * @return 返回一个runner
     */
    public KafkaConsumerRunner getConsumerOrInit(String topicName) {
        KafkaConsumerRunner consumer = getConsumer(topicName);
        if (consumer != null) {
            logger.info(LogUtil.makeKafkaLog(LogMsg.KAFKA, OptionDetails.KAFKA_CONSUMER_TOPIC_GET_EXIST, topicName));
            return consumer;
        }
        logger.info(LogUtil.makeKafkaLog(LogMsg.KAFKA, OptionDetails.KAFKA_CONSUMER_TOPIC_GET_NO_EXIST, topicName));
        initKafkaTopic(topicName);
        return getConsumer(topicName);
    }


}
