package main.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {
    /**
     * 配置RestHighLevelClient对象
     * 将该对象交给Spring容器去管理
     *
     * @return RestHighLevelClient对象
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        //若有多个，可以传一个数组
                        new HttpHost("192.168.56.106", 3200, "http")));
    }
}
