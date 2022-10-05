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
        return logMsg.getName() + "---" + methodName + "---" + optionDetails.getType() + "---" +
                optionDetails.getStatus() + "---" + optionDetails.getMsg();
    }

    public static String makeOptionDetails(LogMsg logMsg, OptionDetails optionDetails, int taskId) {
        return logMsg.getName() + "---" + optionDetails.getType() + "---" + optionDetails.getStatus() +
                "---" + optionDetails.getMsg() + "---taskId:" + taskId;
    }

    public static String makeOptionDetails(int userId, LogMsg logMsg, OptionDetails optionDetails) {
        return logMsg.getName() + "---" + optionDetails.getType() + "---" + optionDetails.getStatus() +
                "---" + optionDetails.getMsg() + "---userId:" + userId;
    }

    public static String makeOptionDetails(LogMsg logMsg, OptionDetails optionDetails, TestProto.User user) {
        return logMsg.getName() + "---" + optionDetails.getType() + "---" + optionDetails.getStatus() +
                "---" + optionDetails.getMsg() +
                "userId:" + user.getUserId() + ",userName:" + user.getUserName();
    }

    public static String makeOptionDetails(LogMsg logMsg, OptionDetails optionDetails) {
        return logMsg.getName() + "---" + optionDetails.getType() + "---" + optionDetails.getStatus() +
                "---" + optionDetails.getMsg();
    }

    public static String filterUrl(LogMsg logMsg, OptionDetails optionDetails) {
        return logMsg.getName() + "---" + optionDetails.getType() + "---" + optionDetails.getStatus() +
                "---" + optionDetails.getMsg();
    }

    public static String makeOptionDetails(LogMsg logMsg, OptionDetails optionDetails, int taskId, int userId) {
        return logMsg.getName() + "---" + optionDetails.getType() + "---" + optionDetails.getStatus() +
                "---" + optionDetails.getMsg() + "---taskId:" + taskId + "---userId:" + userId;
    }

    public static String makeKafkaLog(LogMsg logMsg, OptionDetails optionDetails, String topicName) {
        return logMsg.getName() + "---" + optionDetails.getType() + "---" + optionDetails.getStatus() +
                "---" + optionDetails.getMsg() + "---topicName:" + topicName;
    }
}
