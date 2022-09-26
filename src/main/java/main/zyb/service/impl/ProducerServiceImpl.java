package main.zyb.service.impl;
import com.google.protobuf.InvalidProtocolBufferException;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.util.ProtocolUtil;
import main.util.ThreadLocalUtil;
import main.yxl.publicContext.PublicContext;
import main.zyb.service.ProducerService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pto.TestProto;

@Service
public class ProducerServiceImpl implements ProducerService {

    private final static Logger logger = LogUtil.getLogger(ProducerServiceImpl.class);

    @Autowired
    private ProtocolUtil protocolUtil;

    @Autowired
    private PublicContext publicContext;
    /**
     * 任务发布者开始任务
     * @param data
     * @return
     */
    @Override
    public byte[] prod_StartTask(byte[] data) {
        TestProto.S2C_ProdStartTask.Builder S2C_result = TestProto.S2C_ProdStartTask.newBuilder();
        TestProto.C2S_ProdStartTask builder = null;
        try {
            builder = TestProto.C2S_ProdStartTask.parseFrom(data);
        }catch (InvalidProtocolBufferException e) {
            //错误处理打印错误日志、返回错误信息
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.PROD_TASK_START_FLOAT));

            /**
             * 获取ResponseMsg对象，set数据，并赋值给S2C_result，并返回给controller
             */
            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.PROD_TASK_START_FLOAT.getMsg());
            S2C_result.setMsg(ResponseMsg);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_START_TASK);
        }
        int taskId = builder.getTaskId();
        TestProto.ResponseMsg.Builder resultProto = publicContext.prod_StartTask(taskId);
        S2C_result.setMsg(resultProto);
        byte[] bytes = S2C_result.buildPartial().toByteArray();
        return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_START_TASK);
    }

    /**
     * 任务发布者关闭任务
     * @param data
     * @return
     */
    @Override
    public byte[] prod_EndTask(byte[] data) {
        TestProto.S2C_prod_EndTask.Builder S2C_result = TestProto.S2C_prod_EndTask.newBuilder();
        TestProto.C2S_prod_EndTask builder = null;


        try{
            builder = TestProto.C2S_prod_EndTask.parseFrom(data);
        }catch (Exception e)
        {
            //错误处理打印错误日志、返回错误信息
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.END_TASK_USERID_FLOAT));

            /**
             * 获取ResponseMsg对象，set数据，并赋值给S2C_result，并返回给controller
             */
            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.END_TASK_USERID_FLOAT.getMsg());
            S2C_result.setMsg(ResponseMsg);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_END_TASK);
        }
        //获取任务id
        int task = builder.getTaskId();
        TestProto.ResponseMsg.Builder resultProto = publicContext.prod_EndTask(task);
        S2C_result.setMsg(resultProto);
        byte[] bytes = S2C_result.buildPartial().toByteArray();
        return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_PROD_END_TASK);
    }

    /**
     * 任务发布者添加任务
     * @param data
     * @return
     */
    @Override
    public byte[] prod_AddTask(byte[] data) {
        TestProto.S2C_prodAddTask.Builder S2C_result = TestProto.S2C_prodAddTask.newBuilder();
        TestProto.C2S_prodAddTask builder = null;
        try {
            builder = TestProto.C2S_prodAddTask.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            //错误处理打印错误日志、返回错误信息
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.ACCESS_DATA_INTERFACE_ERROR));

            /**
             * 获取ResponseMsg对象，set数据，并赋值给S2C_result，并返回给controller
             */
            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_ERROR.getMsg());
            S2C_result.setMsg(ResponseMsg);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_ADD_TASK);
        }
        /**
         * 获取builder的userId和session中的userId进行对比，判断是否满足条件
         */
        if (ThreadLocalUtil.getCurrentUser().getUserId()==builder.getTask().getUserId())
        {
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.ADD_TASK_USERID_TRUE));
            //封装对象调用函数
            TestProto.Task.Builder task =  TestProto.Task.newBuilder(builder.getTask());
            TestProto.ResponseMsg.Builder builderResponseMsg = publicContext.prod_AddTask(task);
            /**
             * 调用publicContext.prod_AddTask方法得到返回信息，并返回给controller
             */
            S2C_result.setMsg(builderResponseMsg);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_ADD_TASK);
        }
        else
        {
            //封装错误log和返回错误信息
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.ADD_TASK_USERID_FLOAT));
            /**
             * 获取ResponseMsg对象，set数据，并赋值给S2C_result，并返回给controller
             */
            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.ACCESS_DATA_INTERFACE_ERROR.getMsg());
            S2C_result.setMsg(ResponseMsg);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_ADD_TASK);

        }

    }
    /**
     * 生产者获取任务测试结果集
     * @param data
     * @return
     */
    @Override
    public byte[] prod_GetResult(byte[] data) {
        TestProto.S2C_prod_GetResult.Builder S2C_result = TestProto.S2C_prod_GetResult.newBuilder();
        TestProto.C2S_prod_GetResult builder = null;


        try{
            builder = TestProto.C2S_prod_GetResult.parseFrom(data);
        }catch (Exception e)
        {
            //错误处理打印错误日志、返回错误信息
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.PROD_GetResult_FLOAT));

            /**
             * 获取ResponseMsg对象，set数据，并赋值给S2C_result，并返回给controller
             */
            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.PROD_GetResult_FLOAT.getMsg());
            S2C_result.setMsg(ResponseMsg);
            TestProto.TaskResult taskResult = null;
            S2C_result.setTaskResult(taskResult);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_GETRESULT);
        }
        //获取任务id
        int taskId = builder.getTaskId();
        TestProto.TaskResult.Builder GetResult = publicContext.prod_GetResult(taskId);
        TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
        ResponseMsg.setStatus(true);
        ResponseMsg.setMsg(OptionDetails.PROD_GetResult_TRUE.getMsg());
        S2C_result.setMsg(ResponseMsg);
        S2C_result.setTaskResult(GetResult);
        byte[] bytes = S2C_result.buildPartial().toByteArray();
        return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_PROD_GETRESULT);
    }

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
    public byte[] prod_GetAllAddTasks(byte[] data) {
        TestProto.S2C_prod_GetAllAddTasks.Builder S2C_result = TestProto.S2C_prod_GetAllAddTasks.newBuilder();
        TestProto.C2S_prod_GetAllAddTasks builder = null;
        try {
            builder = TestProto.C2S_prod_GetAllAddTasks.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            //错误处理打印错误日志、返回错误信息
            logger.info(LogUtil.makeOptionDetails(LogMsg.ACCESS_DATA_INTERFACE, OptionDetails.PROD_GET_ALL_ADD_TASKS_FLOAT));
            TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder();
            ResponseMsg.setStatus(false);
            ResponseMsg.setMsg(OptionDetails.PROD_GET_ALL_ADD_TASKS_FLOAT.getMsg());
            TestProto.ProdAddTasks tasks = null;
            S2C_result.setMsg(ResponseMsg);
            S2C_result.setTasks(tasks);
            byte[] bytes = S2C_result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_GET_ALL_ADD_TASKS);
        }
        //获取计算结果填入到返回值中，并返回给客户端
        TestProto.ProdAddTasks.Builder tasks = publicContext.prod_GetAllAddTasks(builder.getUserId());
        TestProto.ResponseMsg.Builder  ResponseMsg = TestProto.ResponseMsg.newBuilder().setMsg(OptionDetails.PROD_GET_ALL_ADD_TASKS_TRUE.getMsg()).setStatus(true);
        S2C_result.setMsg(ResponseMsg);
        S2C_result.setTasks(tasks);
        byte[] bytes = S2C_result.buildPartial().toByteArray();
        return protocolUtil.encodeProtocol(bytes,bytes.length,TestProto.Types.S2C_PROD_GET_ALL_ADD_TASKS);
    }


}
