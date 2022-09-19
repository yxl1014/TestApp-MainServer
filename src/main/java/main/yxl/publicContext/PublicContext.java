package main.yxl.publicContext;

import main.pto.TestProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author main.yxl
 * @date: 2022/9/19 上午10:43
 */

@Service
public class PublicContext {

    @Autowired
    private Map<Integer, TestProto.Task> taskMap;
}
