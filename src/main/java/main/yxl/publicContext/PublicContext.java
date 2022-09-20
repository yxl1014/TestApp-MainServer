package main.yxl.publicContext;

import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.yxl.publicContext.config.contextBean.TaskMapUtil;
import main.yxl.publicContext.config.contextBean.UserMapUtil;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pto.TestProto;

import java.util.HashMap;
import java.util.List;


/**
 * @author main.yxl
 * @date: 2022/9/19 上午10:43
 */

@Service
public class PublicContext {

    private final static Logger logger = LogUtil.getLogger(PublicContext.class);

    @Autowired
    private TaskMapUtil taskMap;

    @Autowired
    private UserMapUtil userMap;

    private final HashMap<Integer, TestProto.TaskConduct.Builder> taskConductMap = new HashMap<>();


    /**
     * 任务发布者开始任务
     * 这里判断任务是否存在，若不存在则返回false
     * 若任务状态为true说明已经开始，返回false
     * 其他不判断
     */
    public TestProto.ResponseMsg.Builder prod_StartTask(int taskId) {
        TestProto.ResponseMsg.Builder builder = TestProto.ResponseMsg.newBuilder();

        //查询任务是否存在
        TestProto.Task.Builder task = taskMap.getTask(taskId);
        if (task == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.P_TASK_NOT_FOUND, taskId));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.P_TASK_NOT_FOUND.getMsg());
            return builder;
        }

        //查询这个任务是否存在
/*        if (taskConductMap.containsKey(taskId)) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.P_TASK_EXIST, taskId));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.P_TASK_EXIST.getMsg());
            return builder;
        }*/
        if (task.getStatus()) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.P_TASK_EXIST, taskId));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.P_TASK_EXIST.getMsg());
            return builder;
        }
        task.setStatus(true);
        TestProto.TaskConduct.Builder newBuilder = TestProto.TaskConduct.newBuilder();
        newBuilder.setTaskId(taskId);
        this.taskConductMap.put(taskId, newBuilder);


        logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.P_TASK_START_OK, taskId));
        builder.setMsg(OptionDetails.P_TASK_START_OK.getMsg());
        builder.setStatus(true);
        //TODO 同时这里需要调用scheduled建立kafka以及logstash的链接
        return builder;
    }


    /**
     * 任务发布者关闭任务
     * 将任务状态设置为false
     * 将taskConductMap中此任务的进行时任务删掉
     * 将user的doingTask设置成0
     * 调用测试结果收集器生成测试结果集
     */
    public TestProto.ResponseMsg.Builder prod_EndTask(int taskId) {
        TestProto.ResponseMsg.Builder builder = TestProto.ResponseMsg.newBuilder();

        TestProto.Task.Builder task = this.taskMap.getTask(taskId);
        if (task == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.P_TASK_NOT_FOUND, taskId));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.P_TASK_NOT_FOUND.getMsg());
            return builder;
        }

        if (!task.getStatus()) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.P_TASK_NO_START, taskId));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.P_TASK_NO_START.getMsg());
            return builder;
        }

        TestProto.TaskConduct.Builder conduct = this.taskConductMap.get(taskId);
        if (conduct == null) {
            logger.error(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.PUBLIC_CONDUCT_NOT_FOUND, taskId));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.PUBLIC_CONDUCT_NOT_FOUND.getMsg());
            return builder;
        }

        task.setStatus(false);

        List<Integer> userIdsList = conduct.getUserIdsList();
        for (Integer id : userIdsList) {
            TestProto.S_User.Builder sUser = this.userMap.getSUser(id);
            sUser.setDoingTaskId(0);
        }
        //TODO 这里关闭kafka以及logstash

        //TODO 这里生成结果集

        this.taskConductMap.remove(taskId);
        return builder;
    }
}
