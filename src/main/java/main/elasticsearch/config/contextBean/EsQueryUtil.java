package main.elasticsearch.config.contextBean;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class EsQueryUtil {
    @Autowired
    RestHighLevelClient client;
    //搜索
    public SearchHits  getResultToId(int test)  {
        //搜索条件
        SearchResponse response = null;
        SearchRequest request = new SearchRequest().indices("kafkamsg").source(new SearchSourceBuilder().query(QueryBuilders.termQuery("taskId",test)));
        try {
            response = client.search(request,RequestOptions.DEFAULT);
        }catch (IOException e )
        {
            //异常日志处理，写入日志
        }
        SearchHits hits = response.getHits();

        return hits;
    }
    /**
     * 单位时间成功    数量转换
     * @param map
     * @return
     */
    public Map numberOfTimesSuccess(Map<Long, Boolean>  map)
    {
        Map<Long, Long> mapResult = new LinkedHashMap();

        Iterator entries = map.entrySet().iterator();
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

        return mapResult;
    }
    /**
     * 单位时间失败    数量转换
     * @param map
     * @return
     */
    public Map numberOfTimesFail(Map<Long, Boolean>  map)
    {
        Map<Long, Long> mapResult = new LinkedHashMap();

        Iterator entries = map.entrySet().iterator();
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
        return mapResult;
    }
}
