package main.filter;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.util.ThreadLocalUtil;
import main.util.User;
import main.yxl.publicContext.config.contextBean.UserMapUtil;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pto.TestProto;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static main.util.Unsign.unsign;

@WebFilter(filterName = "MainDoFilter")
public class MainDoFilter implements Filter {

    @Autowired
    private UserMapUtil userMap;

    private final static Logger logger = LogUtil.getLogger(MainDoFilter.class);

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
        //记录访问地址日志
        //到日志枚举添加msg
        //再将参数放入到filterUrl中
        OptionDetails.FILTER_MSG.setMsg(requestURI);
        logger.info(LogUtil.filterUrl(LogMsg.FILTER,OptionDetails.FILTER_MSG));
        String token =request.getHeader("token");

        if (request.getSession().getAttribute("user")!=null)
        {
            return;
        }

        if(token==null)
        {
            logger.info(LogUtil.makeOptionDetails(LogMsg.FILTER, OptionDetails.FILTER_MSG_ERROR));
            response.getWriter().write("error：token为空,请重新登陆");
        }else
        {
            try{
                byte[] data = unsign(token,byte[].class);
                /**
                 * 添加到session中,并添加到userMap中
                 */
                logger.info(LogUtil.makeOptionDetails(LogMsg.FILTER, OptionDetails.FILTER_MSG_OK));
                request.getSession().setAttribute("user",data);
                request.getSession().setMaxInactiveInterval(1200);
                userMap.addUser(data);

                /**
                 * 将userId存放到ThreadLocal中
                 */
                TestProto.User U  = TestProto.User.parseFrom(data);
                User user = new User();
                int userId = U.getUserId();
                user.setUserId(userId);
                ThreadLocalUtil.addCurrentUser(user);

                logger.info(LogUtil.makeOptionDetails(LogMsg.LOGIN, OptionDetails.LOGIN_OK));
            }catch (Exception e)
            {
                /**
                 * 日志方法
                 */
                response.getWriter().write("error：token验证不通过");
                logger.info(LogUtil.makeOptionDetails(LogMsg.LOGIN, OptionDetails.LOGIN_TOKEN_ERROR));
            }
        }

    }

    @Override
    public void destroy() {
        System.out.println("程序关闭或者主动调用了关闭filter方法");
    }
}
