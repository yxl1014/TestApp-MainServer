package main.yxl.publicContext.config.contextBean;

import com.google.protobuf.InvalidProtocolBufferException;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.yxl.mapper.PublicContextMapper;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pto.TestProto;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yxl
 * @date: 2022/9/19 下午3:45
 */
public class TaskMapUtil {
    @Autowired
    private PublicContextMapper publicContextMapper;

    private final static Logger logger = LogUtil.getLogger(TaskMapUtil.class);
    private final HashMap<Integer, TestProto.Task.Builder> tasks = new HashMap<>();

    /**
     * 向TaskContext中添加Task对象
     */
    public boolean addTask(byte[] proto) {
        TestProto.Task task = null;
        try {
            task = TestProto.Task.parseFrom(proto);
        } catch (InvalidProtocolBufferException e) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.UTIL, OptionDetails.PROTOBUF_ERROR, Method.class.getName()));
            return false;
        }

        //若这个任务本来就在map里，则添加失败
        if (this.tasks.containsKey(task.getTaskId())) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.UTIL, OptionDetails.TASK_EXIST, Method.class.getName()));
            return false;
        } else {
            //若用户不在map里则查询数据库
            byte[] data = publicContextMapper.findDataByTaskId(task.getTaskId());
            if (data != null) {
                TestProto.Task.Builder builder;
                try {
                    builder = TestProto.Task.parseFrom(data).toBuilder();
                } catch (InvalidProtocolBufferException e) {
                    logger.info(LogUtil.makeOptionDetails(LogMsg.UTIL, OptionDetails.PROTOBUF_ERROR, Method.class.getName()));
                    return false;
                }
                //最后再更新map
                this.tasks.put(task.getTaskId(), builder);
                logger.info(LogUtil.makeOptionDetails(LogMsg.UTIL, OptionDetails.TASK_EXIST, Method.class.getName()));
                return false;
            } else {
                this.tasks.put(task.getTaskId(), task.toBuilder());
                return true;
            }
        }
    }

    /**
     * 通过TaskId获取Task对象
     */
    public TestProto.Task.Builder getTask(int taskId) {
        if (this.tasks.containsKey(taskId)) {
            return this.tasks.get(taskId);
        }
        //若用户不在map里则查询数据库
        byte[] data = publicContextMapper.findDataByTaskId(taskId);
        if (data == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.UTIL, OptionDetails.TASK_NOT_FOUND, Method.class.getName()));
            return null;
        }
        TestProto.Task.Builder builder;
        try {
            builder = TestProto.Task.parseFrom(data).toBuilder();
        } catch (InvalidProtocolBufferException e) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.UTIL, OptionDetails.PROTOBUF_ERROR, Method.class.getName()));
            return null;
        }
        this.tasks.put(builder.getTaskId(), builder);
        return builder;
    }

    /**
     * 返回TaskMap的clone对象，用于定时落盘
     */
    public Map<Integer, TestProto.Task.Builder> cloneTaskMaps() {
        return (HashMap<Integer, TestProto.Task.Builder>) this.tasks.clone();
    }

}
