package main.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.protobuf.util.JsonFormat;
import main.elasticsearch.config.contextBean.EsQueryUtil;
import main.util.ProtocolUtil;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import pto.TestProto;

import java.util.*;

/**
 * @Author zhang
 * @Date 2022/10/17
 */
public class ElasticsearchContext {
    @Autowired
    private EsQueryUtil esQueryUtil;
/**
 * 1.通过任务id查询出单个任务所有的任务测试情况，统计成功率
 * 2.通过任务id查询出单个任务所有的任务测试情况，按照时间轴对应成功率，成功率突然下降则为任务程序异常或者宕机
 */
    public TestProto.TaskResult.Builder getResultToId(int taskId)
    {
        TestProto.TaskResult.Builder taskResult = TestProto.TaskResult.newBuilder();
        SearchHits hits = esQueryUtil.getResultToId(taskId);
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

        Map mapResult = esQueryUtil.numberOfTimesSuccess(map);
        String mapResultJson = JSONObject.toJSONString(mapResult);
        taskResult.setResultJson(mapResultJson);
        taskResult.setFailNumber(failNumber);
        taskResult.setSuccessNumber(successNumber);
        taskResult.setResultNum(histNumber);
        return taskResult;
    }

    /**
     * 管理员删除测试的任务
     */
    public boolean deleteToId(int taskId)
    {



        return false;
    }
}
