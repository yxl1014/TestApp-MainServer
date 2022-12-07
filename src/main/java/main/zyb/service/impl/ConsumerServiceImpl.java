package main.zyb.service.impl;
import com.google.protobuf.InvalidProtocolBufferException;
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
//            TestProto.Task task = null;
            S2C_result.setMsg(ResponseMsg);
//            S2C_result.setTask(task);
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
        logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.PROD_Get_Task_TRUE));
        return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_GET_TASK);
    }

    @Override
    public byte[] cons_TakeTask(byte[] data) {
        TestProto.S2C_Cons_TakeTask.Builder S2C_result = TestProto.S2C_Cons_TakeTask.newBuilder();
        TestProto.C2S_Cons_TakeTask builder = null;
        try {
            builder = TestProto.C2S_Cons_TakeTask.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            //错误处理打印错误日志、返回错误信息

            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.CONS_TAKE_TASk_FLOAT));

            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.CONS_TAKE_TASk_FLOAT.getMsg());
            S2C_result.setMsg(ResponseMsg);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_GET_TASK);
        }
        int userId = builder.getUserId();
        int taskId = builder.getTaskId();
        TestProto.ResponseMsg.Builder responseMsg = publicContext.cons_TakeTask(taskId,userId);
        S2C_result.setMsg(responseMsg);
        byte[] bytes = S2C_result.buildPartial().toByteArray();
        logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.CONS_TAKE_TASk_TRUE));
        return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_CONS_TAKE_TASK);
    }

    @Override
    public byte[] cons_StartTask(byte[] data) {
        TestProto.S2C_Cons_StartTask.Builder S2C_result = TestProto.S2C_Cons_StartTask.newBuilder();
        TestProto.C2S_Cons_StartTask builder = null;
        try {
            builder = TestProto.C2S_Cons_StartTask.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            //错误处理打印错误日志、返回错误信息

            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.CONS_START_TASk_FLOAT));

            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.CONS_TAKE_TASk_FLOAT.getMsg());
            S2C_result.setMsg(ResponseMsg);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_CONS_START_TASK);
        }
        int userId = builder.getUserId();
        int taskId = builder.getTaskId();
        TestProto.ResponseMsg.Builder responseMsg = publicContext.cons_StartTask(taskId,userId);
        S2C_result.setMsg(responseMsg);
        byte[] bytes = S2C_result.buildPartial().toByteArray();
        logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.CONS_START_TASk_TRUE));
        return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_CONS_START_TASK);
    }

    @Override
    public byte[] cons_EndTask(byte[] data) {
        TestProto.S2C_Cons_EndTask.Builder S2C_result = TestProto.S2C_Cons_EndTask.newBuilder();
        TestProto.C2S_Cons_EndTask builder = null;
        try {
            builder = TestProto.C2S_Cons_EndTask.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            //错误处理打印错误日志、返回错误信息

            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.CONS_END_TASk_FLOAT));

            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.CONS_END_TASk_FLOAT.getMsg());
            S2C_result.setMsg(ResponseMsg);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_CONS_END_TASK);
        }
        int userId = builder.getUserId();
        int taskId = builder.getTaskId();
        TestProto.ResponseMsg.Builder responseMsg = publicContext.cons_EndTask(taskId,userId);
        S2C_result.setMsg(responseMsg);
        byte[] bytes = S2C_result.buildPartial().toByteArray();
        logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.CONS_END_TASk_TRUE));
        return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_CONS_END_TASK);
    }

    @Override
    public byte[] cons_DelTask(byte[] data) {
        TestProto.S2C_Cons_DelTask.Builder S2C_result = TestProto.S2C_Cons_DelTask.newBuilder();
        TestProto.C2S_Cons_DelTask builder = null;
        try {
            builder = TestProto.C2S_Cons_DelTask.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            //错误处理打印错误日志、返回错误信息

            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.CONS_DEL_TASk_FLOAT));

            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.CONS_DEL_TASk_FLOAT.getMsg());
            S2C_result.setMsg(ResponseMsg);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_CNS_DEL_TASK);
        }
        int userId = builder.getUserId();
        int taskId = builder.getTaskId();
        TestProto.ResponseMsg.Builder responseMsg = publicContext.cons_DelTask(taskId,userId);
        S2C_result.setMsg(responseMsg);
        byte[] bytes = S2C_result.buildPartial().toByteArray();
        logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.CONS_DEL_TASk_TRUE));
        return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_CNS_DEL_TASK);
    }

    @Override
    public byte[] cons_AllGetTasks(byte[] data) {
        TestProto.S2C_Cons_AllGetTasks.Builder S2C_result = TestProto.S2C_Cons_AllGetTasks.newBuilder();
        TestProto.C2S_Cons_AllGetTasks builder = null;
        try {
            builder = TestProto.C2S_Cons_AllGetTasks.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            //错误处理打印错误日志、返回错误信息
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.CONS_ALL_GET_TASkS_FLOAT));
            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.CONS_ALL_GET_TASkS_FLOAT.getMsg());
            S2C_result.setMsg(ResponseMsg);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_CONS_ALL_GET_TASKS);
        }
        int userId = builder.getUserId();
        TestProto.ConsGetTasks.Builder tasks = publicContext.cons_AllGetTasks(userId);
        TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
        ResponseMsg.setStatus(true);
        ResponseMsg.setMsg(OptionDetails.CONS_ALL_GET_TASkS_TRUE.getMsg());
        S2C_result.setMsg(ResponseMsg);
        S2C_result.setTasks(tasks);
        byte[] bytes = S2C_result.buildPartial().toByteArray();
        logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.CONS_ALL_GET_TASkS_TRUE));
        return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_CONS_ALL_GET_TASKS);
    }
}
