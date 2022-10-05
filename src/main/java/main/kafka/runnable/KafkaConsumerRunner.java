package main.kafka.runnable;

import main.logs.LogUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author yxl
 * @date: 2022/10/5 下午5:00
 */
public class KafkaConsumerRunner implements Runnable {

    private final static Logger logger = LogUtil.getLogger(KafkaConsumerRunner.class);
    private final String topicName;
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final KafkaConsumer<String, String> consumer;

    //TODO 还要加上logstash的接口
    public KafkaConsumerRunner(String topicName, KafkaConsumer<String, String> consumer) {
        this.topicName = topicName;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        try {
            consumer.subscribe(Collections.singleton(topicName));
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                // TODO: 这里只是一个简单的处理  之后需要改
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s\n",
                            record.offset(), record.key(), record.value());
                }
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            if (!closed.get()) {
                logger.error("topicName : " + topicName + ",error :" + e.getMessage());
                throw e;
            }
        } finally {
            consumer.close();
        }
    }

    // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }

    public AtomicBoolean getClosed() {
        return closed;
    }
}
