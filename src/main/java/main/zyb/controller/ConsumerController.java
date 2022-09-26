package main.zyb.controller;

import main.annotation.Consumer;
import main.annotation.Producer;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.util.ProtocolUtil;
import main.zyb.service.ProducerService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ProducerService producerService;

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
        return producerService.getTask(temp);
    }
}
