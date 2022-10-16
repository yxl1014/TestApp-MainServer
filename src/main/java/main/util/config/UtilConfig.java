package main.util.config;

import main.ly.config.LruBean.LruUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import main.util.ProtocolUtil;
import pto.TestProto;

/**
 * @author yxl.testapp.domain.yxl
 * @date: 2022/9/12 下午2:05
 * 工具bean载入配置文件
 */

@Configuration
public class UtilConfig {

    @Bean
    public ProtocolUtil getProtocolUtil() {
        return new ProtocolUtil();
    }

}
