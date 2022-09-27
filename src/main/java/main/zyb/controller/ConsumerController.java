package main.zyb.controller;

import main.annotation.Consumer;
import main.annotation.Producer;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.util.ProtocolUtil;
import main.zyb.service.ProducerService;
import main.zyb.service.impl.ConsumerServiceImpl;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pto.TestProto;

@RestController
@RequestMapping("Consumer")//消费者
public class ConsumerController {

    private final static Logger logger = LogUtil.getLogger(ConsumerController.class);

    @Autowired
    private ProtocolUtil protocolUtil;

    @Autowired
    private ConsumerServiceImpl consumerService;

    /**
     * 生产者获取发布的所有任务的详细信息
     */
    @Consumer
    @PostMapping("/getTaskInformation")//查询任务接口   获取任务信息
    public byte[] getTaskInformation(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE  , OptionDetails.ACCESS_DATA_INTERFACE_NULL));
            TestProto.S2C_Get_Task.Builder result = TestProto.S2C_Get_Task.newBuilder();
            TestProto.ResponseMsg.Builder ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_NULL.getMsg());
            ResponseMsg.setStatus(false);
            TestProto.Task task=null;
            result.setTask(task);
            result.setMsg(ResponseMsg);
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_GET_TASK);
        }
        return consumerService.getTask(temp);
    }
    /**
     * 消费者接受任务
     */
    @Consumer
    @PostMapping("/ConsTakeTask")
    public byte[] consTakeTask(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE  , OptionDetails.ACCESS_DATA_INTERFACE_NULL));
            TestProto.S2C_Cons_TakeTask.Builder result = TestProto.S2C_Cons_TakeTask.newBuilder();
            TestProto.ResponseMsg.Builder ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_NULL.getMsg());
            ResponseMsg.setStatus(false);
            result.setMsg(ResponseMsg);
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_CONS_TAKE_TASK);
        }
        return consumerService.cons_TakeTask(temp);
    }
    /**
     * 消费者开始任务
     */
    @Consumer
    @PostMapping("/consStartTask")
    public byte[] consStartTask(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE  , OptionDetails.ACCESS_DATA_INTERFACE_NULL));
            TestProto.S2C_Cons_StartTask.Builder result = TestProto.S2C_Cons_StartTask.newBuilder();
            TestProto.ResponseMsg.Builder ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_NULL.getMsg());
            ResponseMsg.setStatus(false);
            result.setMsg(ResponseMsg);
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_CONS_START_TASK);
        }
        return consumerService.cons_StartTask(temp);
    }
    /**
     * 消费者结束任务
     */
    @Consumer
    @PostMapping("/consEndTask")
    public byte[] consEndTask(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE  , OptionDetails.ACCESS_DATA_INTERFACE_NULL));
            TestProto.S2C_Cons_EndTask.Builder result = TestProto.S2C_Cons_EndTask.newBuilder();
            TestProto.ResponseMsg.Builder ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_NULL.getMsg());
            ResponseMsg.setStatus(false);
            result.setMsg(ResponseMsg);
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_CONS_END_TASK);
        }
        return consumerService.cons_EndTask(temp);
    }
    /**
     * 消费者放弃任务
     */
    @Consumer
    @PostMapping("/consDelTask")
    public byte[] consDelTask(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE  , OptionDetails.ACCESS_DATA_INTERFACE_NULL));
            TestProto.S2C_Cons_DelTask.Builder result = TestProto.S2C_Cons_DelTask.newBuilder();
            TestProto.ResponseMsg.Builder ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_NULL.getMsg());
            ResponseMsg.setStatus(false);
            result.setMsg(ResponseMsg);
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_CNS_DEL_TASK);
        }
        return consumerService.cons_DelTask(temp);
    }
    /**
     * 消费者获取接受的所有任务的详细信息
     */
    @Consumer
    @PostMapping("/consAllGetTasks")
    public byte[] consAllGetTasks(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE  , OptionDetails.ACCESS_DATA_INTERFACE_NULL));
            TestProto.S2C_Cons_AllGetTasks.Builder result = TestProto.S2C_Cons_AllGetTasks.newBuilder();
            TestProto.ResponseMsg.Builder ResponseMsg = TestProto.ResponseMsg.newBuilder();
            TestProto.ConsGetTasks consGetTasks = null;
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_NULL.getMsg());
            ResponseMsg.setStatus(false);
            result.setTasks(consGetTasks);
            result.setMsg(ResponseMsg);
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_CONS_ALL_GET_TASKS);
        }
        return consumerService.cons_AllGetTasks(temp);
    }
}
