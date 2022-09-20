package main.interceptor;

import com.mysql.cj.Session;
import main.filter.MainDoFilter;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.pto.TestProto;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PowerInterceptor implements HandlerInterceptor {

    private final static Logger logger = LogUtil.getLogger(MainDoFilter.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String session = (String)request.getSession().getAttribute("user");
        TestProto.User user = TestProto.User.parseFrom(session.getBytes());
        //判断登录状态，管理员登录直接放行
        try
        {
            if (user.getUserType())
            {
                logger.info(LogUtil.makeOptionDetails(LogMsg.IDENTITY, OptionDetails.IDENTITY_administrators));
                return user.getUserType();
            }
        }catch (Exception e)
        {
            logger.info(LogUtil.makeOptionDetails(LogMsg.IDENTITY, OptionDetails.IDENTITY_USER));
            response.sendRedirect(request.getContextPath() + "/error.html");
        }
        return false;

    }
}
