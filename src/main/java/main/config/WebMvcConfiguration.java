package main.config;

import main.interceptor.PowerInterceptor;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 重写addCorsMappings()解决跨域问题
     * 配置：允许http请求进行跨域访问
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册Interceptor拦截器(Interceptor这个类是我们自己写的拦截器类)
        InterceptorRegistration registration = registry.addInterceptor(new PowerInterceptor());
        //addPathPatterns()方法添加需要拦截的路径
        //所有路径都被拦截
        registration.addPathPatterns("/**");
        //excludePathPatterns()方法添加不拦截的路径
        //添加不拦截路径
        registration.excludePathPatterns(//添加不拦截路径，需要拦截的路径不填写，只需要判断用户状态，是管理员返回t,不是管理员返回f
//                //登录
//                "/login",
//                //退出登录
//                "/loginOut",
//                //获取验证码
//                "/getCode",
//                //发送短信
//                "/sendshortMessage",
//                //重置账号
//                "/unsealaccount",
//                //文件上传
//                "/uploadImg",
//                //html静态资源
//                "/**/*.html",
//                //js静态资源
//                "/**/*.js",
//                //css静态资源
//                "/**/*.css"
        );
    }
}
