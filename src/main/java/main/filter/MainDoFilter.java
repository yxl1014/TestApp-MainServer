package main.filter;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.util.JWTUtil;
import main.util.ProtocolUtil;
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
import java.nio.charset.StandardCharsets;

import static main.util.Unsign.unsign;

@WebFilter(filterName = "MainDoFilter",urlPatterns = "/*")
public class MainDoFilter implements Filter {
    @Autowired
    private ProtocolUtil protocolUtil;

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
            //符合条件进入下层义务
            filterChain.doFilter(servletRequest,servletResponse);
        }

        if(token==null)
        {
            logger.info(LogUtil.makeOptionDetails(LogMsg.FILTER, OptionDetails.FILTER_MSG_ERROR));
            TestProto.ResponseMsg.Builder msg = TestProto.ResponseMsg.newBuilder();
            msg.setMsg("token is null");
            msg.setStatus(false);
            byte[] bytes = msg.buildPartial().toByteArray();
            byte[] bytes1 = protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_GET_TASK);
            response.getWriter().write(new String(bytes1, StandardCharsets.UTF_8));
        }else
        {

                byte[] data = JWTUtil.unsign(token,byte[].class);
                if(data!=null) {
                    /**
                     * 添加到session中,并添加到userMap中
                     */
                    logger.info(LogUtil.makeOptionDetails(LogMsg.FILTER, OptionDetails.FILTER_MSG_OK));
                    request.getSession().setAttribute("user", data);
                    request.getSession().setMaxInactiveInterval(1200);
                    //userMap.addUser(data);

                    /**
                     * 将userId存放到ThreadLocal中
                     */
                    TestProto.User U = TestProto.User.parseFrom(data);
                    User user = new User();
                    int userId = U.getUserId();
                    user.setUserId(userId);
                    ThreadLocalUtil.addCurrentUser(user);
                    logger.info(LogUtil.makeOptionDetails(LogMsg.LOGIN, OptionDetails.LOGIN_OK));
                    //进入下层业务
                    filterChain.doFilter(servletRequest,servletResponse);
                }
            /**
             * 日志方法
             */
            TestProto.ResponseMsg.Builder msg = TestProto.ResponseMsg.newBuilder();
            msg.setMsg("token is error");
            msg.setStatus(false);
            byte[] bytes = msg.buildPartial().toByteArray();
            byte[] bytes1 = protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_GET_TASK);
            response.getWriter().write(new String(bytes1, StandardCharsets.UTF_8));
        }

    }

    @Override
    public void destroy() {
        System.out.println("程序关闭或者主动调用了关闭filter方法");
    }
}
