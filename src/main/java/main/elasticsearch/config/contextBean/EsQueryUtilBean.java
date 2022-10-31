package main.elasticsearch.config.contextBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author zhang
 * @Date 2022/10/17
 */
@Configuration
public class EsQueryUtilBean {
    @Bean
    public EsQueryUtil getEsQueryUtil()
    {
        return new EsQueryUtil();
    }
}