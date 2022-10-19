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
    private final Map<Integer, TestProto.TaskShell.Builder> shellMap = new HashMap<>();

    private final Map<Integer, Boolean> canRun;


    /**
     * key:脚本ID
     * value:调用返回信息
     * 返回信息
     */
    private final ConcurrentHashMap<Integer, String> conditionMsgMap = new ConcurrentHashMap<>();


    public KafkaContext(Map<Integer, TestProto.TaskShell> shellMap) {
        for (Map.Entry<Integer, TestProto.TaskShell> entry : shellMap.entrySet()) {
            this.shellMap.put(entry.getKey(), entry.getValue().toBuilder());
        }
        this.canRun = new HashMap<>();
        for (int id : shellMap.keySet()) {
            if (shellMap.get(id).getCondition() == 0) {
                this.canRun.put(id, true);
            } else {
                this.canRun.put(id, false);
            }
        }
    }

    public synchronized void addResponse(TestProto.KafkaMsg.Builder msgBuilder) {
        conditionMsgMap.put(msgBuilder.getShellId(), msgBuilder.getResponseMsg());
        for (Map.Entry<Integer, TestProto.TaskShell.Builder> entry : this.shellMap.entrySet()) {
            if (entry.getValue().getCondition() == msgBuilder.getShellId()) {
                this.canRun.put(entry.getKey(), true);
                //TODO 这里还需要解析出需要的数据 将他加到shell中
            }
        }
    }
}
