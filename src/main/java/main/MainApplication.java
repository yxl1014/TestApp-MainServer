package main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author yxl
 * @date: 2022/9/19 上午11:27
 */

@SpringBootApplication
@MapperScan("main.yxl.mapper")
@ServletComponentScan(basePackages = "main.filter")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
