package main.elasticsearch.config.contextBean;
import com.google.protobuf.util.JsonFormat;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import pto.TestProto;

import java.io.IOException;
import java.util.*;

public class EsQueryUtil {
    @Autowired
    RestHighLevelClient client;

    /**
     * 条件查询返回值是hits (查询出来的数据集合)
     * @param taskId
     * @return
     */
    public SearchHits  getResultToTaskId(int taskId) throws IOException {
        //搜索条件
        SearchResponse response = null;
        SearchRequest request = new SearchRequest().indices("kafkamsg").source(new SearchSourceBuilder().query(QueryBuilders.termQuery("taskId",taskId)));
        try {
            response = client.search(request,RequestOptions.DEFAULT);
            client.close();
        }catch (IOException e )
        {
            //异常日志处理，写入日志
        }
        SearchHits hits = response.getHits();

        client.close();
        return hits;
    }


    /**
     * 通过userId查询出本用户所有测试的任务数据，通过taskId进行分类，统计成功率、按照时间轴对应成功率
     * 此方法获取此用户的所有测试的taskId 是一个set集合;
     * @param userId
     * @return
     * @throws IOException
     */
    public Set getResultToUserId(int userId) throws IOException {
        SearchRequest request = new SearchRequest().indices("kafkamsg").source(new SearchSourceBuilder().query(QueryBuilders.termQuery("userId",userId)));
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        HashSet taskIdSet = new HashSet();
        for (SearchHit hit:hits)
        {
            TestProto.KafkaMsg.Builder  builder= TestProto.KafkaMsg.newBuilder();
            JsonFormat.parser().merge(hit.getSourceAsString(),builder);
            taskIdSet.add(builder.getTaskId());
        }
        client.close();
        return taskIdSet;
    }

    /**
     * 单位时间成功    数量转换
     * @param map
     * @return
     */
    public Map numberOfTimesSuccess(Map<Long, Boolean>  map) throws IOException {
        Map<Long, Long> mapResult = new LinkedHashMap();
        long time=0;
        Boolean success;

        long successNumber=0;
        long ones =1 ;
        for (Map.Entry entry : map.entrySet())
        {

            success = (Boolean) entry.getValue();
            if (success==true)
            {
                successNumber++;
            }
            time = time + (Long) entry.getKey();
            if (time>1000L)
            {
                time=0l;
                mapResult.put(ones,successNumber);
            }
            ones++;
        }
        client.close();
        return mapResult;
    }
    /**
     * 单位时间失败    数量转换
     * @param map
     * @return
     */
    public Map numberOfTimesFail(Map<Long, Boolean>  map) throws IOException {
        Map<Long, Long> mapResult = new LinkedHashMap();
        long time=0;
        Boolean success;
        long failNumber=0;
        long ones =1 ;
        for (Map.Entry entry : map.entrySet())
        {

            success = (Boolean) entry.getValue();
            if (success!=true)
            {
                failNumber++;
            }
            time = time + (Long) entry.getKey();
            if (time>1000L)
            {
                time=0l;
                mapResult.put(ones,failNumber);
            }
            ones++;
        }
        client.close();
        return mapResult;
    }


    /**
     * 管理员通过taskId删除测试数据
     * @param taskId
     * @return
     */
    public boolean deleteDocToTaskId(int taskId) throws IOException {
        SearchResponse response = null;
        //BulkResponse responses = null;
        SearchRequest request = new SearchRequest().indices("kafkamsg").source(new SearchSourceBuilder().query(QueryBuilders.termQuery("taskId",taskId)));
        try {
            response = client.search(request,RequestOptions.DEFAULT);
        }catch (IOException e )
        {
            //异常日志处理，写入日志
            client.close();
            return false;
        }
        BulkRequest delRequest = new BulkRequest();
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            delRequest.add(new DeleteRequest().index("kafkamsg").id(hit.getId()));
        }
        try {
            client.bulk(delRequest, RequestOptions.DEFAULT);
        }catch (IOException e )
        {
            //异常日志处理，写入日志
            client.close();
            return false;
        }
        client.close();
        return true;
    }

}
