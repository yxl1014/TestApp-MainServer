package main.zyb.controller;
import com.google.protobuf.InvalidProtocolBufferException;
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
@RequestMapping("/producer")//生产者接口
public class ProducerController {

    private final static Logger logger = LogUtil.getLogger(ConsumerController.class);

    @Autowired
    private ProtocolUtil protocolUtil;

    @Autowired
    private ProducerService producerService;
    @Producer
    @PostMapping("/release")//发布任务接口
    public byte[] release(@RequestBody byte[] data) throws InvalidProtocolBufferException {

        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.ACCESS_DATA_INTERFACE_NULL));
            TestProto.S2C_prodAddTask.Builder result = TestProto.S2C_prodAddTask.newBuilder();
            TestProto.ResponseMsg.Builder ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_NULL.getMsg());
            ResponseMsg.setStatus(false);
            result.setMsg(ResponseMsg);
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_ADD_TASK);
        }
        return producerService.prod_AddTask(temp);
    }

    @Producer
    @PostMapping("/Start")//开始任务任务接口
    public byte[] Start(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.ACCESS_DATA_INTERFACE_NULL));
            TestProto.S2C_ProdStartTask.Builder result = TestProto.S2C_ProdStartTask.newBuilder();
            TestProto.ResponseMsg.Builder ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_NULL.getMsg());
            ResponseMsg.setStatus(false);
            result.setMsg(ResponseMsg);
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_START_TASK);
        }
        return producerService.prod_StartTask(temp);
    }

    @Producer
    @PostMapping("/close")//关闭任务接口
    public byte[] close(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.ACCESS_DATA_INTERFACE_NULL));
            TestProto.S2C_prod_EndTask.Builder result = TestProto.S2C_prod_EndTask.newBuilder();
            TestProto.ResponseMsg.Builder ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_NULL.getMsg());
            ResponseMsg.setStatus(false);
            result.setMsg(ResponseMsg);
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_END_TASK);
        }
        return producerService.prod_EndTask(temp);
    }



    @Producer
    @PostMapping("/GetTestResults")//获取测试结果集接口
    public byte[] GetTestResults(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.ACCESS_DATA_INTERFACE_NULL));

            TestProto.S2C_prod_GetResult.Builder result = TestProto.S2C_prod_GetResult.newBuilder();

            TestProto.ResponseMsg.Builder ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_NULL.getMsg());
            ResponseMsg.setStatus(false);
            TestProto.TaskResult taskResult = null;
            result.setMsg(ResponseMsg);
            result.setTaskResult(taskResult);
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_GETRESULT);
        }
        return producerService.prod_GetResult(temp);
    }



    /**
     * 生产者获取发布的所有任务的详细信息
     */
    @Producer
    @PostMapping("/queryAllAddTasks")//查询任务接口   获取任务信息
    public byte[] queryAllAddTasks(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE , OptionDetails.ACCESS_DATA_INTERFACE_NULL));
            TestProto.S2C_prod_GetAllAddTasks.Builder result  =TestProto.S2C_prod_GetAllAddTasks.newBuilder();
            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_NULL.getMsg());
            TestProto.ProdAddTasks prodAddTasks = null;
            result.setMsg(ResponseMsg);
            result.setTasks(prodAddTasks);
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_PROD_GET_ALL_ADD_TASKS);
        }
        return producerService.prod_GetAllAddTasks(temp);
    }

    /**
     * 生产者获取发布的所有任务的详细信息
     */

    @Producer
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
