package main.annotation;

import java.lang.annotation.*;

/**
 * @author yxl
 * @date: 2022/9/19 上午11:55
 */

@Target({ ElementType.PARAMETER, ElementType.METHOD }) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented//生成文档
public @interface Consumer {
}
