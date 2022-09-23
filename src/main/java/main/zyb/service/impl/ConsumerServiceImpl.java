package main.zyb.service.impl;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.util.ProtocolUtil;
import main.yxl.publicContext.PublicContext;
import main.zyb.service.ConsumerService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pto.TestProto;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final static Logger logger = LogUtil.getLogger(ConsumerServiceImpl.class);

    @Autowired
    private ProtocolUtil protocolUtil;

    @Autowired
    private PublicContext publicContext;
    /**
     * 生产者和消费者公用逻辑、获取任务信息
     */
    @Override
    public byte[] getTask(byte[] data) {
        TestProto.S2C_Get_Task.Builder S2C_result = TestProto.S2C_Get_Task.newBuilder();
        TestProto.C2S_Get_Task builder = null;
        try{
            builder = TestProto.C2S_Get_Task.parseFrom(data);
        }catch (Exception e)
        {
            //错误处理打印错误日志、返回错误信息

            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.PROD_Get_Task_FLOAT));

            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.PROD_Get_Task_FLOAT.getMsg());
            TestProto.Task task = null;
            S2C_result.setMsg(ResponseMsg);
            S2C_result.setTask(task);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_GET_TASK);
        }
        //获取任务id
        int taskId = builder.getTaskId();
        //获取结果
        TestProto.Task.Builder Task = publicContext.getTask(taskId);
        //封装log
        TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
        ResponseMsg.setStatus(true);
        ResponseMsg.setMsg(OptionDetails.PROD_Get_Task_TRUE.getMsg());
        S2C_result.setMsg(ResponseMsg);
        S2C_result.setTask(Task);
        byte[] bytes = S2C_result.buildPartial().toByteArray();
        return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_GET_TASK);
    }

    @Override
    public byte[] prod_GetAddTasks(byte[] data) {
        return new byte[0];
    }
}
