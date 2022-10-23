package main.elasticsearch;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.util.JsonFormat;
import main.elasticsearch.config.contextBean.EsQueryUtil;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pto.TestProto;
import java.util.*;

/**
 * @Author zhang
 * @Date 2022/10/17
 */
@Service
public class ElasticsearchContext {
    @Autowired
    private EsQueryUtil esQueryUtil;

    private final static Logger logger = LogUtil.getLogger(ElasticsearchContext.class);
    /**
     * 1.通过任务id查询出单个任务所有的任务测试情况，统计成功率
     * 2.通过任务id查询出单个任务所有的任务测试情况，按照时间轴对应成功率，成功率突然下降则为任务程序异常或者宕机
     */
    /**
     * 返回测试成功！时间轴信息、总数、成功数、失败数
     * @param taskId
     * @return
     */
    public TestProto.TaskResult.Builder getResultToIdSuccess(int taskId) {
        TestProto.TaskResult.Builder taskResult = TestProto.TaskResult.newBuilder();
        SearchHits hits = null;
        hits = esQueryUtil.getResultToTaskId(taskId);
        if (hits==null)
        {
            return null;
        }
        long histNumber = (int) hits.getTotalHits().value;
        long failNumber=0;
        long successNumber=0;
        Map<Long, Boolean> map = new LinkedHashMap();
        TestProto.KafkaMsg.Builder kfkBuilder = TestProto.KafkaMsg.newBuilder();
        for (SearchHit hit: hits)
        {
            try {
                JsonFormat.parser().merge(hit.getSourceAsString(),kfkBuilder);
            }catch (Exception e)
            {
                /**
                 * 日志异常处理
                 */
                logger.info(LogUtil.makeOptionDetails(LogMsg.ELASTICSEARCH, OptionDetails.ELASTICSEARCH_JSON_FORMAT));
            }
            if (kfkBuilder.getSuccess()==true)
            {
                successNumber++;
            }else
            {
                failNumber++;
            }
            map.put(kfkBuilder.getCostTime(),kfkBuilder.getSuccess());
        }

        Map mapResult = null;
        mapResult = esQueryUtil.numberOfTimesSuccess(map);
        String mapResultJson = JSONObject.toJSONString(mapResult);
        taskResult.setResultJson(mapResultJson);
        taskResult.setFailNumber(failNumber);
        taskResult.setSuccessNumber(successNumber);
        taskResult.setResultNum(histNumber);
        taskResult.setTaskId(taskId);
        return taskResult;
    }

    /**
     * 返回测试失败！时间轴信息，总数，成功数，失败数
     * @param taskId
     * @return
     */
    public TestProto.TaskResult.Builder getResultToIdFail(int taskId)  {
        TestProto.TaskResult.Builder taskResult = TestProto.TaskResult.newBuilder();
        SearchHits hits = null;
        hits = esQueryUtil.getResultToTaskId(taskId);
        if (hits==null)
        {
            return null;
        }
        long histNumber = (int) hits.getTotalHits().value;
        long failNumber=0;
        long successNumber=0;
        Map<Long, Boolean> map = new LinkedHashMap();
        TestProto.KafkaMsg.Builder kfkBuilder = TestProto.KafkaMsg.newBuilder();
        for (SearchHit hit: hits)
        {
            try {
                JsonFormat.parser().merge(hit.getSourceAsString(),kfkBuilder);
            }catch (Exception e)
            {
                /**
                 * 日志异常处理
                 */
            }
            if (kfkBuilder.getSuccess()==true)
            {
                successNumber++;
            }else
            {
                failNumber++;
            }
            map.put(kfkBuilder.getCostTime(),kfkBuilder.getSuccess());
        }

        Map mapResult = null;
        mapResult = esQueryUtil.numberOfTimesFail(map);
        String mapResultJson = JSONObject.toJSONString(mapResult);
        taskResult.setResultJson(mapResultJson);
        taskResult.setFailNumber(failNumber);
        taskResult.setSuccessNumber(successNumber);
        taskResult.setResultNum(histNumber);
        taskResult.setTaskId(taskId);
        return taskResult;
    }

    /**
     * 通过userId查询出本用户所有测试的任务数据，通过taskId进行分类，统计成功率、按照时间轴对应成功率
     * 将list里面存放多个TaskResult；和一个list的总数
     */

    public TestProto.TaskResultList.Builder  getResultToUserIdSuccess(int userId) {
        Set tasksId = esQueryUtil.getResultToUserId(userId);
        List list = new ArrayList();
        for(Object t:tasksId){
            list.add(getResultToIdSuccess((Integer) t));
        }
        String taskListJson = JSONObject.toJSONString(list);
        TestProto.TaskResultList.Builder taskResultList = TestProto.TaskResultList.newBuilder();
        taskResultList.setTaskResultList(taskListJson);
        taskResultList.setTaskResultNum(tasksId.size());
        return taskResultList;
    }
    /**
     * 通过userId查询出本用户所有测试的任务数据，通过taskId进行分类，统计失败率、按照时间轴对应成功率
     * 将list里面存放多个TaskResult；和一个list的总数
     */
    public TestProto.TaskResultList.Builder  getResultToUserIdFail(int userId)  {
        Set tasksId = esQueryUtil.getResultToUserId(userId);
        List<TestProto.TaskResult.Builder> list = new ArrayList();
        for(Object t:tasksId){
            list.add(getResultToIdFail((Integer) t));
        }
        String taskListJson = JSONObject.toJSONString(list);
        TestProto.TaskResultList.Builder taskResultList = TestProto.TaskResultList.newBuilder();
        taskResultList.setTaskResultList(taskListJson);
        taskResultList.setTaskResultNum(tasksId.size());
        return taskResultList;
    }

    /**
     * 管理员删除测试的任务
     */
    public TestProto.S2C_Es_DeleteToTestId.Builder deleteToTestId(int taskId) {
        boolean isDelete = esQueryUtil.deleteDocToTaskId(taskId);
        TestProto.S2C_Es_DeleteToTestId.Builder builder = TestProto.S2C_Es_DeleteToTestId.newBuilder();
        TestProto.ResponseMsg.Builder ResponseMsg = TestProto.ResponseMsg.newBuilder();
        ResponseMsg.setStatus(isDelete);
        builder.setMsg(ResponseMsg);
        return builder;
    }
}
