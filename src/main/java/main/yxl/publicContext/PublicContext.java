package main.yxl.publicContext;

import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.yxl.publicContext.config.contextBean.TaskMapUtil;
import main.yxl.publicContext.config.contextBean.UserMapUtil;
import main.yxl.publicContext.scheduled.TaskScheduled;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pto.TestProto;

import java.util.ArrayList;
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

    public HashMap<Integer, TestProto.TaskConduct.Builder> getTaskConductMap() {
        return taskConductMap;
    }

    @Autowired
    private TaskScheduled taskScheduled;

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

        //调度器初始化
        taskScheduled.prodStartTask(taskId);
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
        taskScheduled.prodStopTask(taskId);

        //TODO 这里生成结果集

        this.taskConductMap.remove(taskId);
        return builder;
    }


    /**
     * 生产者添加任务
     *
     * @param task 需要添加的任务builder
     * @return ResponseMsg.Builder
     */
    public TestProto.ResponseMsg.Builder prod_AddTask(TestProto.Task.Builder task) {
        TestProto.ResponseMsg.Builder builder = TestProto.ResponseMsg.newBuilder();
        TestProto.Task.Builder temp = taskMap.getTask(task.getTaskId());
        if (temp != null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.TASK_EXIST, task.getTaskId()));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.TASK_EXIST.getMsg());
            return builder;
        }
        boolean b = taskMap.addTask(task.build().toByteArray());
        if (!b) {
            logger.error(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.SYSTEM_ERROR));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.SYSTEM_ERROR.getMsg());
        } else {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.P_TASK_ADD_OK, task.getTaskId()));
            builder.setMsg(OptionDetails.P_TASK_ADD_OK.getMsg());
            builder.setStatus(true);
        }
        return builder;
    }


    /**
     * 生产者获取任务测试结果集
     */
    public TestProto.TaskResult.Builder prod_GetResult(int taskId) {
        TestProto.TaskResult.Builder builder = null;
        TestProto.Task.Builder task = this.taskMap.getTask(taskId);
        if (task == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.P_TASK_NOT_FOUND, taskId));
            return builder;
        }
        builder = task.getResult().toBuilder();
        return builder;
    }


    /**
     * 生产者获取发布的所有任务的详细信息
     */
    public TestProto.ProdAddTasks.Builder prod_GetAllAddTasks(int userId) {
        TestProto.ProdAddTasks.Builder builder = TestProto.ProdAddTasks.newBuilder();
        TestProto.S_User.Builder sUser = this.userMap.getSUser(userId);
        if (sUser == null) {
            logger.info(LogUtil.makeOptionDetails(userId, LogMsg.PUBLIC_CONTEXT, OptionDetails.P_GET_ADD_TASKS_NO_USER));
            return null;
        }
        List<Integer> addTasksList = sUser.getAddTasksList();
        for (Integer tid : addTasksList) {
            builder.addTasks(this.getTask(tid));
        }
        return builder;
    }

    //------------------------------------------------------------------------------------------------------------------


    /**
     * 生产者和消费者公用逻辑、获取任务信息
     */
    public TestProto.Task.Builder getTask(int taskId) {
        return this.taskMap.getTask(taskId);
    }


    //------------------------------------------------------------------------------------------------------------------


    /**
     * 消费者接受任务
     *
     * @param taskId 任务id
     * @param userId 用户id
     * @return ResponseMsg.Builder
     */
    public TestProto.ResponseMsg.Builder cons_TakeTask(int taskId, int userId) {
        TestProto.ResponseMsg.Builder builder = TestProto.ResponseMsg.newBuilder();
        TestProto.S_User.Builder sUser = userMap.getSUser(userId);

        //判断是否已经接受该任务
        if (sUser.getTaskIdsList().contains(taskId)) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.C_TAKE_IS_TAKE, taskId, userId));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.C_TAKE_IS_TAKE.getMsg());
            return builder;
        }

        userMap.getSUser(userId).addAddTasks(taskId);
        taskMap.getTask(taskId).addTaskCons(userId);

        builder.setStatus(true);
        builder.setMsg(OptionDetails.C_TAKE_TASK_OK.getMsg());
        return builder;
    }

    /**
     * 消费者开始任务
     *
     * @param taskId 任务id
     * @param userId 用户id
     * @return ResponseMsg.Builder
     */
    public TestProto.ResponseMsg.Builder cons_StartTask(int taskId, int userId) {
        TestProto.ResponseMsg.Builder builder = TestProto.ResponseMsg.newBuilder();

        //判断是否已经接受该任务
        if (userMap.getSUser(userId).getTaskIdsList().contains(taskId)) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.C_START_NO_TAKE, taskId, userId));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.C_START_NO_TAKE.getMsg());
            return builder;
        }

        //判断该任务是否可以接受
        if (!taskMap.getTask(taskId).getStatus()) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.C_START_TASK_NOT_START, taskId, userId));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.C_START_TASK_NOT_START.getMsg());
            return builder;
        }

        //为conduct添加该用户
        TestProto.TaskConduct.Builder builder1 = taskConductMap.get(taskId);
        if (builder1 == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.SYSTEM_ERROR));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.SYSTEM_ERROR.getMsg());
            return builder;
        }
        builder1.setUserIds(userId, -1);

        //将用户当前任务设置成这个任务的id
        userMap.getSUser(userId).setDoingTaskId(taskId);

        //重新分配任务脚本
        this.taskScheduled.scheduledOne(taskId);

        builder.setStatus(true);
        builder.setMsg(OptionDetails.C_START_TASK_OK.getMsg());
        return builder;
    }

    /**
     * 消费者结束任务
     *
     * @param taskId 任务id
     * @param userId 用户id
     * @return ResponseMsg.Builder
     */
    public TestProto.ResponseMsg.Builder cons_EndTask(int taskId, int userId) {
        TestProto.ResponseMsg.Builder builder = TestProto.ResponseMsg.newBuilder();
        TestProto.S_User.Builder sUser = userMap.getSUser(userId);
        if (sUser.getDoingTaskId() == 0) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.C_END_TASK_NO_DOING));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.C_END_TASK_NO_DOING.getMsg());
            return builder;
        }
        //将conduct中的任务去掉
        TestProto.TaskConduct.Builder builder1 = this.taskConductMap.get(taskId);
        if (builder1 == null) {
            logger.error(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.SYSTEM_ERROR));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.SYSTEM_ERROR.getMsg());
            return builder;
        }
        builder1.removeShellMapping(userId);

        List<Integer> userIdsList = builder1.getUserIdsList();
        List<Integer> temp = new ArrayList<>();
        for (Integer i : userIdsList) {
            if (i != userId)
                temp.add(i);
        }
        builder1.clearUserIds();
        builder1.addAllUserIds(temp);

        //将正在执行的任务设为0
        sUser.setDoingTaskId(0);


        //重新分配任务脚本
        this.taskScheduled.scheduledOne(taskId);

        builder.setStatus(true);
        builder.setMsg(OptionDetails.C_END_TASK_OK.getMsg());
        return builder;
    }

    /**
     * 消费者放弃任务
     *
     * @param taskId 任务id
     * @param userId 用户id
     * @return ResponseMsg.Builder
     */
    public TestProto.ResponseMsg.Builder cons_DelTask(int taskId, int userId) {
        TestProto.ResponseMsg.Builder builder = TestProto.ResponseMsg.newBuilder();
        TestProto.S_User.Builder sUser = userMap.getSUser(userId);
        List<Integer> addTasksList = sUser.getTaskIdsList();

        //判断用户是否接受了该任务
        if (!addTasksList.contains(taskId)) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.PUBLIC_CONTEXT, OptionDetails.C_DEL_TASK_NO_EXIST));
            builder.setStatus(false);
            builder.setMsg(OptionDetails.C_DEL_TASK_NO_EXIST.getMsg());
            return builder;
        }

        //删除该用户中的taskId
        List<Integer> temp = new ArrayList<>();
        for (Integer i : addTasksList) {
            if (i != taskId)
                temp.add(i);
        }
        sUser.clearAddTasks();
        sUser.addAllAddTasks(temp);

        //删除该任务中的userId
        temp.clear();
        TestProto.Task.Builder task = this.taskMap.getTask(taskId);
        List<Integer> taskConsList = task.getTaskConsList();
        for (Integer i : taskConsList) {
            if (i != userId)
                temp.add(i);
        }
        task.clearTaskCons();
        task.addAllTaskCons(temp);

        if (sUser.getDoingTaskId() == taskId) {
            return cons_EndTask(taskId, userId);
        }

        builder.setStatus(true);
        builder.setMsg(OptionDetails.C_DEL_TASK_OK.getMsg());
        return builder;
    }


    /**
     * 消费者获取接受的所有任务的详细信息
     */
    public TestProto.ConsGetTasks.Builder cons_AllGetTasks(int userId) {
        TestProto.ConsGetTasks.Builder builder = TestProto.ConsGetTasks.newBuilder();
        TestProto.S_User.Builder sUser = this.userMap.getSUser(userId);
        List<Integer> addTasksList = sUser.getTaskIdsList();
        for (Integer tid : addTasksList) {
            builder.addTasks(this.getTask(tid));
        }
        return builder;
    }

}
