package main.elasticsearch.config.contextBean.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ElasticSearchConfig {
    /**
     * 配置RestHighLevelClient对象
     * 将该对象交给Spring容器去管理
     *
     * @return RestHighLevelClient对象
     */
    @Autowired
    ElasticsearchPropertiesBean bean;
    @Bean
    public RestHighLevelClient restHighLevelClient() {

        List hostname = bean.getHostname();
        HttpHost[] httpHosts = new HttpHost[hostname.size()];
        for (int i = 0; i <hostname.size() ; i++) {
            httpHosts[i] = new HttpHost(hostname.get(i).toString(), bean.port, "http");
        }
        return new RestHighLevelClient(
                RestClient.builder(
                        httpHosts));
                         //若有多个，可以传一个数组
                        //new HttpHost("192.168.56.106", 3200, "http")));
    }
}
