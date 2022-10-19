package main.yxl.publicContext.scheduled;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import main.kafka.consumer.common.KafkaConsumerConnectPoll;
import main.kafka.consumer.common.KafkaContext;
import main.kafka.producer.KafkaProducerPoll;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.util.FinalData;
import main.yxl.publicContext.PublicContext;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pto.TestProto;

import java.util.*;

/**
 * 任务处理定时器
 *
 * @author yxl
 * @date: 2022/10/13 下午4:51
 */

@Component
public class TaskScheduled {


    private final static Logger logger = LogUtil.getLogger(TaskScheduled.class);

    @Autowired
    private PublicContext publicContext;

    @Autowired
    private KafkaProducerPoll producerPoll;

    @Autowired
    private KafkaConsumerConnectPoll consumerPoll;


    /**
     * 每分钟重新调度一次所有任务
     */
    @Scheduled(cron = "0 * * * * ?")
    public void taskShell() {
        scheduledAll();
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void test() {
        logger.info(new Date(System.currentTimeMillis()));
    }


    public void prodStartTask(int taskId) {
        TestProto.Task.Builder task = publicContext.getTask(taskId);
        //初始化任务测试结果监听线程
        consumerPoll.initKafkaTopic(taskId, new KafkaContext(task.getShellMap()));
        //初始化同步调用推送者
        boolean producer = producerPoll.createProducer(taskId);
        logger.info(LogUtil.makeScheduledLog(LogMsg.CONTROL, OptionDetails.CONTROL_START_S2C_TOPIC, producer));
    }

    public void prodStopTask(int taskId) {
        //关闭结果集监听线程
        consumerPoll.shutdown(taskId);
        //关闭同步调用推送者
        producerPoll.closeProducer(taskId);
    }

    /**
     * TODO 这个破调度器效率巨慢  得优化 时间复杂度 O(800n)
     * 重新调度每一个任务
     */
    public void scheduledAll() {
        HashMap<Integer, TestProto.TaskConduct.Builder> taskConductMap = this.publicContext.getTaskConductMap();
        for (Map.Entry<Integer, TestProto.TaskConduct.Builder> entry : taskConductMap.entrySet()) {
            //获取调度进行时上下文
            KafkaContext kafkaContext = this.consumerPoll.getConsumer(entry.getKey()).getKafkaContext();
            if (kafkaContext == null) {
                logger.warn(LogUtil.makeScheduledLog(LogMsg.CONTROL,
                        OptionDetails.CONTROL_RE_CONTROL_ERROR_KAFKA_CONTEXT_NOT_EXIST, entry.getKey()));
                continue;
            }
            //获取任务上下文
            TestProto.TaskConduct.Builder builder = entry.getValue();
            if (builder == null) {
                logger.warn(LogUtil.makeScheduledLog(LogMsg.CONTROL,
                        OptionDetails.CONTROL_RE_CONTROL_ERROR_CONDUCT_NOT_EXIST, entry.getKey()));
                continue;
            }


            //可执行脚本id
            List<Integer> canRunShell = new ArrayList<>();
            for (Map.Entry<Integer, Boolean> en : kafkaContext.getCanRun().entrySet()) {
                if (en.getValue())
                    canRunShell.add(en.getKey());
            }

            //获取脚本数量
            int shellSize = kafkaContext.getShellMap().size();

            //可执行脚本数量
            int canRunSize = canRunShell.size();

            //统计当前脚本执行用户数量
            int[] count = new int[shellSize + 1];

            //获取全部用户id
            List<Integer> allUser = builder.getUserIdsList();
            //获取用户脚本映射
            Map<Integer, Integer> shellMappingMap = builder.getShellMappingMap();
            //申明一个没有脚本的用户
            ArrayDeque<Integer> noShellUser = new ArrayDeque<>();

            int average = allUser.size() / canRunSize;

            //计算每个脚本当前有多少人在执行 以及统计出有哪些用户没有脚本
            for (int uid : allUser) {
                if (shellMappingMap.containsKey(uid)) {
                    if (count.length < average) {
                        count[shellMappingMap.get(uid)]++;
                    } else {
                        builder.removeShellMapping(uid);
                        noShellUser.offer(uid);
                    }
                } else {
                    noShellUser.offer(uid);
                }
            }


            //重新分配任务
            for (int sid : canRunShell) {
                if (count[sid] < average) {
                    int more = average - count[sid];
                    for (int x = 0; x < more; x++) {
                        if (noShellUser.size() != 0)
                            builder.putShellMapping(noShellUser.poll(), sid);
                    }
                }
            }

            //获取当前时间
            long nowTime = System.currentTimeMillis();
            long startTime = nowTime + FinalData.DELAY_TIME;

            //设置时间
            for (TestProto.TaskShell.Builder shell : kafkaContext.getShellMap().values()) {
                shell.setStartTime(startTime).setIntervalTime(2000);
            }

            //向client端推送运行脚本
            for (Map.Entry<Integer, Integer> e : builder.getShellMappingMap().entrySet()) {
                try {
                    producerPoll.sendMsg(builder.getTaskId(), e.getKey(),
                            JsonFormat.printer().print(kafkaContext.getShellMap().get(e.getValue())));
                } catch (InvalidProtocolBufferException ex) {
                    logger.error(LogUtil.makeErrorMsg(LogMsg.CONTROL, ex.getMessage()));
                }
            }

        }
    }


    /**
     * 重新分配某个任务
     *
     * @param taskId 任务ID
     */
    public void scheduledOne(int taskId) {
        //获取任务上下文
        TestProto.TaskConduct.Builder builder = this.publicContext.getTaskConductMap().get(taskId);
        if (builder == null) {
            logger.warn(LogUtil.makeScheduledLog(LogMsg.CONTROL,
                    OptionDetails.CONTROL_RE_CONTROL_ERROR_CONDUCT_NOT_EXIST, taskId));
            return;
        }
        //获取调度进行时上下文
        KafkaContext kafkaContext = this.consumerPoll.getConsumer(taskId).getKafkaContext();
        if (kafkaContext == null) {
            logger.warn(LogUtil.makeScheduledLog(LogMsg.CONTROL,
                    OptionDetails.CONTROL_RE_CONTROL_ERROR_KAFKA_CONTEXT_NOT_EXIST, taskId));
            return;
        }

        //可执行脚本id
        List<Integer> canRunShell = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> en : kafkaContext.getCanRun().entrySet()) {
            if (en.getValue())
                canRunShell.add(en.getKey());
        }

        //获取脚本数量
        int shellSize = kafkaContext.getShellMap().size();

        //可执行脚本数量
        int canRunSize = canRunShell.size();

        //统计当前脚本执行用户数量
        int[] count = new int[shellSize + 1];

        //获取全部用户id
        List<Integer> allUser = builder.getUserIdsList();
        //获取用户脚本映射
        Map<Integer, Integer> shellMappingMap = builder.getShellMappingMap();
        //申明一个没有脚本的用户
        ArrayDeque<Integer> noShellUser = new ArrayDeque<>();

        int average = allUser.size() / canRunSize;

        //计算每个脚本当前有多少人在执行 以及统计出有哪些用户没有脚本
        for (int uid : allUser) {
            if (shellMappingMap.containsKey(uid)) {
                if (count.length < average) {
                    count[shellMappingMap.get(uid)]++;
                } else {
                    builder.removeShellMapping(uid);
                    noShellUser.offer(uid);
                }
            } else {
                noShellUser.offer(uid);
            }
        }


        //重新分配任务
        for (int sid : canRunShell) {
            if (count[sid] < average) {
                int more = average - count[sid];
                for (int x = 0; x < more; x++) {
                    if (noShellUser.size() != 0)
                        builder.putShellMapping(noShellUser.poll(), sid);
                }
            }
        }

        //获取当前时间
        long nowTime = System.currentTimeMillis();
        long startTime = nowTime + FinalData.DELAY_TIME;

        //设置时间
        for (TestProto.TaskShell.Builder shell : kafkaContext.getShellMap().values()) {
            shell.setStartTime(startTime).setIntervalTime(2000);
        }

        //向client端推送运行脚本
        for (Map.Entry<Integer, Integer> e : builder.getShellMappingMap().entrySet()) {
            try {
                producerPoll.sendMsg(builder.getTaskId(), e.getKey(),
                        JsonFormat.printer().print(kafkaContext.getShellMap().get(e.getValue())));
            } catch (InvalidProtocolBufferException ex) {
                logger.error(LogUtil.makeErrorMsg(LogMsg.CONTROL, ex.getMessage()));
            }
        }

    }
}
