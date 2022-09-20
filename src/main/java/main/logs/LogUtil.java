package main.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pto.TestProto;

import java.lang.reflect.Method;

/**
 * @author yxl
 * @date: 2022/9/12 下午1:05
 */
public class LogUtil {
    public static Logger getLogger(Class<?> clz) {
        return LogManager.getLogger(clz);
    }

    public static String makeLogMsg(LogMsg logMsg, String msg) {
        return logMsg.getName() + "---" + msg;
    }

    public static String makeOptionDetails(LogMsg logMsg, OptionDetails optionDetails, String methodName) {
        return logMsg.getName() + "---" + methodName + "---" + optionDetails.getStatus() + "---" + optionDetails.getMsg();
    }

    public static String makeOptionDetails(LogMsg logMsg, OptionDetails optionDetails, int taskId) {
        return logMsg.getName() + "---" + optionDetails.getStatus() + "---" + optionDetails.getMsg() + "---taskId:" + taskId;
    }

    public static String makeOptionDetails(LogMsg logMsg, OptionDetails optionDetails, TestProto.User user) {
        return logMsg.getName() + "---" + optionDetails.getStatus() + "---" + optionDetails.getMsg() +
                "userId:" + user.getUserId() + ",userName:" + user.getUserName();
    }

    public static String makeOptionDetails(LogMsg logMsg, OptionDetails optionDetails) {
        return logMsg.getName() + "---" + optionDetails.getStatus() + "---" + optionDetails.getMsg();
    }
}
