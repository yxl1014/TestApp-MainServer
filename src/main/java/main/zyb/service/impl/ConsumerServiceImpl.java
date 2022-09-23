package main.zyb.service.impl;
import main.logs.LogUtil;
import main.util.ProtocolUtil;
import main.yxl.publicContext.PublicContext;
import main.zyb.service.ConsumerService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final static Logger logger = LogUtil.getLogger(ConsumerServiceImpl.class);

    @Autowired
    private ProtocolUtil protocolUtil;

    @Autowired
    private PublicContext publicContext;

}
