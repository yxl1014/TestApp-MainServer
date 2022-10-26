package main.ly;

import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.ly.config.LruBean.LruUtil;
import main.yxl.mapper.PublicContextMapper;
import main.yxl.mapper.UserContextMapper;
import main.yxl.publicContext.config.contextBean.TaskMapUtil;
import main.yxl.publicContext.config.contextBean.UserMapUtil;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pto.TestProto;

import java.util.Map;


@Component
public class LoadToMysql {

    private final static Logger logger = LogUtil.getLogger(LoadToMysql.class);


    @Autowired
    private TaskMapUtil taskMap;

    @Autowired
    private UserMapUtil userMap;

    @Autowired
    private UserContextMapper userMapper;

    @Autowired
    private PublicContextMapper publicContextMapper;

    @Scheduled(cron = "* 0/15 * * * ?")
    public void execute(){
        logger.info(LogUtil.makeOptionDetails(LogMsg.SCHEDULED, OptionDetails.SCHEDULED_EXECUTE_START));
        Map<Integer, TestProto.Task.Builder> taskMaps = taskMap.cloneTaskMaps();
        for (Map.Entry<Integer,TestProto.Task.Builder> entry : taskMaps.entrySet()){
            if(publicContextMapper.findDataByTaskId(entry.getKey())!=null){
                publicContextMapper.updatePublicContext(entry.getKey(),entry.getValue().build().toByteArray());
            }else{
                publicContextMapper.insertPublicContext(entry.getKey(),entry.getValue().build().toByteArray());
            }

        }
        Map<Integer, TestProto.S_User.Builder> userMaps = userMap.cloneUserMaps();
        for (Map.Entry<Integer,TestProto.S_User.Builder> entry : userMaps.entrySet()){
            if(userMapper.findDataByUserId(entry.getKey())!=null){
                userMapper.updateUserContext(entry.getKey(),entry.getValue().build().toByteArray());
            }else{
                userMapper.insertUserContext(entry.getKey(),entry.getValue().build().toByteArray());
            }
            userMapper.insertUserContext(entry.getKey(),entry.getValue().build().toByteArray());
        }
        logger.info(LogUtil.makeOptionDetails(LogMsg.SCHEDULED,OptionDetails.SCHEDULED_EXECUTE_OVER));
    }
}
