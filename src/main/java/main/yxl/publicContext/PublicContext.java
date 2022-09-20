package main.yxl.publicContext;

import main.yxl.publicContext.config.contextBean.TaskMapUtil;
import main.yxl.publicContext.config.contextBean.UserMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author main.yxl
 * @date: 2022/9/19 上午10:43
 */

@Service
public class PublicContext {

    @Autowired
    private TaskMapUtil taskMap;

    @Autowired
    private UserMapUtil userMap;
}
