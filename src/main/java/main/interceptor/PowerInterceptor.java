package main.interceptor;
import main.annotation.Consumer;
import main.annotation.Producer;
import main.filter.MainDoFilter;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import org.apache.logging.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pto.TestProto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class PowerInterceptor implements HandlerInterceptor {

    private final static Logger logger = LogUtil.getLogger(MainDoFilter.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            response.sendError(1000, OptionDetails.NO_CONTROLLER.getAll());
            return false;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        byte[] session = (byte[])request.getSession().getAttribute("user");
        TestProto.User user = TestProto.User.parseFrom(session);
        /*
        判断是不是Producer方法
         */
        if (user.getUserType())//判断用户是Producer
        {
            if (method.isAnnotationPresent(Producer.class)) {
                logger.info(LogUtil.makeOptionDetails(LogMsg.INTERCEPTOR, OptionDetails.PRODUCER));
                return true;
            }
            logger.info(LogUtil.makeOptionDetails(LogMsg.INTERCEPTOR, OptionDetails.NO_PRODUCER));

        }
        /*
        判断是不是Consumer方法
         */
        if (!user.getUserType())//判断用户是Consumer
        {
            if (method.isAnnotationPresent(Consumer.class)) {
                logger.info(LogUtil.makeOptionDetails(LogMsg.INTERCEPTOR, OptionDetails.Consumer));
                return true;
            }
            logger.info(LogUtil.makeOptionDetails(LogMsg.INTERCEPTOR, OptionDetails.NO_Consumer));
        }

        return false;
    }
}
