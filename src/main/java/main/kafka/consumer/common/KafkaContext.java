package main.kafka.consumer.common;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pto.TestProto;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yxl
 * @date: 2022/10/12 下午4:17
 */

@Data
public class KafkaContext {


    /**
     * key:脚本ID
     * value:脚本信息
     * 任务脚本
     */
    private final Map<Integer, TestProto.TaskShell> shellMap;


    /**
     * key:脚本ID
     * value:调用返回信息
     * 返回信息
     */
    private final ConcurrentHashMap<Integer, String> conditionMsgMap = new ConcurrentHashMap<>();


    public KafkaContext(Map<Integer, TestProto.TaskShell> shellMap) {
        this.shellMap = shellMap;
    }

    public synchronized void addResponse(TestProto.KafkaMsg.Builder msgBuilder) {
        conditionMsgMap.put(msgBuilder.getShellId(), msgBuilder.getResponseMsg());
    }
}
