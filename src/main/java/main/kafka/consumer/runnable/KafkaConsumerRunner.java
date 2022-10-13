package main.kafka.consumer.runnable;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import main.kafka.consumer.common.KafkaContext;
import main.logs.LogUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.logging.log4j.Logger;
import pto.TestProto;

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

    private final KafkaContext kafkaContext;

    public KafkaConsumerRunner(String topicName, KafkaConsumer<String, String> consumer, KafkaContext kafkaContext) {
        this.topicName = topicName;
        this.consumer = consumer;
        this.kafkaContext = kafkaContext;
    }

    @Override
    public void run() {
        try {
            consumer.subscribe(Collections.singleton(topicName));
            TestProto.KafkaMsg.Builder msgBuilder = TestProto.KafkaMsg.newBuilder();
            int shellId;
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    //获取脚本的id
                    shellId = Integer.parseInt(record.key().split("_")[1]);
                    //判断没有存这个脚本的返回报文
                    if (!this.kafkaContext.getConditionMsgMap().containsKey(shellId)) {
                        JsonFormat.parser().merge(record.value(), msgBuilder);
                        //请求成功了才会存储
                        if (msgBuilder.getSuccess()) {
                            kafkaContext.addResponse(msgBuilder);
                        }
                        System.out.printf("offset = %d, key = %s, value = %s\n",
                                record.offset(), record.key(), record.value());
                    }
                }
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            if (!closed.get()) {
                logger.error("topicName : " + topicName + ",error :" + e.getMessage());
                throw e;
            }
        } catch (InvalidProtocolBufferException e) {
            if (!closed.get()) {
                logger.error("topicName : " + topicName + ",error :" + e.getMessage() + ",json转换失败！");
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
