package main.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "MainDoFilter")
public class MainDoFilter implements Filter {
    private String url;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.url = filterConfig.getInitParameter("main/*");
        System.out.println("过滤器的初始化方法！URL=" + this.url + "，开始访问.........");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();//获取本次请求路径
        System.out.println("拦截到请求："+requestURI);
        String tocken =request.getHeader("tocken");
        if(tocken==null)
        {

        }
        //使用秘钥解密tocken成功继续执行并将未解密的tocken加入到redis中；

    }

    @Override
    public void destroy() {
        System.out.println("程序关闭或者主动调用了关闭filter方法");
    }
}
