package main.zyb.service.impl;
import main.annotation.Producer;
import main.logs.LogUtil;
import main.util.ProtocolUtil;
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
     */
    @Override
    public byte[] prod_StartTask(byte[] data) {
        TestProto.Task builder = null;
        try{
            builder = TestProto.Task.parseFrom(data);
        }catch (Exception e)
        {

        }
        //获取任务id
        int taskId = builder.getTaskId();
        TestProto.ResponseMsg.Builder resultProto = publicContext.prod_StartTask(taskId);
        byte[] result = resultProto.buildPartial().toByteArray();
        return protocolUtil.encodeProtocol(result, result.length, TestProto.Types.S2C_REGISTER);// 为啥类型里面没有这个：ResponseMsg
    }

    /**
     * 任务发布者关闭任务
     * @param data
     * @return
     */
    @Override
    public byte[] prod_EndTask(byte[] data) {

        TestProto.Task builder = null;
        try{
            builder = TestProto.Task.parseFrom(data);
        }catch (Exception e)
        {

        }
        //获取任务id
        int task = builder.getTaskId();
        TestProto.ResponseMsg.Builder resultProto = publicContext.prod_EndTask(task);
        byte[] result = resultProto.buildPartial().toByteArray();
        return protocolUtil.encodeProtocol(result, result.length, TestProto.Types.S2C_REGISTER);// 为啥类型里面没有这个：ResponseMsg
    }

    @Override
    public byte[] prod_AddTask(TestProto.Task data) {
        return new byte[0];
    }


}
