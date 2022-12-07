package main.elasticsearch.config.contextBean.config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/10/30
 */

@Data
@Configuration
@ConfigurationProperties("elasticsearch")
public class ElasticsearchPropertiesBean {

    private List hostname;

    @Value("${elasticsearch.port}")
     public int port;

}
