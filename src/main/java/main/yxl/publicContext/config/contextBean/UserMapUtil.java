package main.yxl.publicContext.config.contextBean;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sun.org.apache.xpath.internal.operations.Equals;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.yxl.mapper.UserContextMapper;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pto.TestProto;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yxl
 * @date: 2022/9/19 下午3:45
 */

public class UserMapUtil {

    @Autowired
    private UserContextMapper userContextMapper;

    private final static Logger logger = LogUtil.getLogger(UserMapUtil.class);
    private final HashMap<Integer, TestProto.S_User.Builder> users = new HashMap<>();

    /**
     * 向UserContext中添加S_User对象
     */
    public boolean addUser(byte[] proto) {
        TestProto.User user = null;
        try {
            user = TestProto.User.parseFrom(proto);
        } catch (InvalidProtocolBufferException e) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.UTIL, OptionDetails.PROTOBUF_ERROR, Method.class.getName()));
            return false;
        }

        //若这个用户本来就在map里，则更新map中用户元数据即可
        if (this.users.containsKey(user.getUserId())) {
            TestProto.S_User.Builder builder = this.users.get(user.getUserId());
            builder.setUser(user);
        } else {
            //若用户不在map里则查询数据库
            byte[] data = userContextMapper.findDataByUserId(user.getUserId());

            TestProto.S_User.Builder builder;

            //若数据库中数据为空则初始化一份数据
            if (data == null) {
                builder = initS_User();
                builder.setUserId(user.getUserId());
                builder.setUser(user);
            } else {
                //若不为空，则更新元数据即可
                try {
                    builder = TestProto.S_User.parseFrom(data).toBuilder();
                    builder.setUser(user);
                } catch (InvalidProtocolBufferException e) {
                    logger.info(LogUtil.makeOptionDetails(LogMsg.UTIL, OptionDetails.PROTOBUF_ERROR, Method.class.getName()));
                    return false;
                }
            }
            //最后再更新map
            this.users.put(user.getUserId(), builder);
        }
        return true;
    }

    /**
     * 通过userId获取S_User对象
     */
    public TestProto.S_User.Builder getSUser(int userId) {
        if (this.users.containsKey(userId)) {
            return this.users.get(userId);
        }
        return null;
    }

    /**
     * 返回userMap的clone对象，用于定时落盘
     * */
    public Map<Integer, TestProto.S_User.Builder> cloneUserMaps() {
        return (HashMap<Integer, TestProto.S_User.Builder>) this.users.clone();
    }


    private TestProto.S_User.Builder initS_User() {
        TestProto.S_User.Builder builder = TestProto.S_User.newBuilder();
        builder.setDoingTaskId(0);
        return builder;
    }
}
