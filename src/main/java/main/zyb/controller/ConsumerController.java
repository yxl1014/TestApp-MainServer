package main.zyb.controller;

import main.annotation.Consumer;
import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import main.util.ProtocolUtil;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pto.TestProto;

@RestController
@RequestMapping("Consumer")//消费者
public class ConsumerController {

    private final static Logger logger = LogUtil.getLogger(ConsumerController.class);

    @Autowired
    private ProtocolUtil protocolUtil;

    /**
     *
     * @param data
     * @return
     * 接收任务接口
     */
    @Consumer
    @PostMapping("/receive")//接收任务接口
    public byte[] receive(@RequestBody byte[] data)
    {   byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.LOGIN, OptionDetails.PROTOBUF_ERROR));
            TestProto.S2C_Login.Builder result = TestProto.S2C_Login.newBuilder();
            result.setStatus(false);
            result.setMsg(OptionDetails.PROTOBUF_ERROR.getMsg());
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_LOGIN);
        }
        return null;
    }


    @Consumer
    @PostMapping("/Start")//开始任务任务接口
    public byte[] Start(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.LOGIN, OptionDetails.PROTOBUF_ERROR));
            TestProto.S2C_Login.Builder result = TestProto.S2C_Login.newBuilder();
            result.setStatus(false);
            result.setMsg(OptionDetails.PROTOBUF_ERROR.getMsg());
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_LOGIN);
        }
        return null;
    }


    @Consumer
    @PostMapping("/end")//结束任务接口
    public byte[] end(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.LOGIN, OptionDetails.PROTOBUF_ERROR));
            TestProto.S2C_Login.Builder result = TestProto.S2C_Login.newBuilder();
            result.setStatus(false);
            result.setMsg(OptionDetails.PROTOBUF_ERROR.getMsg());
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_LOGIN);
        }
        return null;
    }


    @Consumer
    @PostMapping("/query")//查询任务接口
    public byte[] query(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.LOGIN, OptionDetails.PROTOBUF_ERROR));
            TestProto.S2C_Login.Builder result = TestProto.S2C_Login.newBuilder();
            result.setStatus(false);
            result.setMsg(OptionDetails.PROTOBUF_ERROR.getMsg());
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_LOGIN);
        }
        return null;
    }


    @Consumer
    @PostMapping("/queryUser")//查询用户任务信息接口
    public byte[] queryUser(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.LOGIN, OptionDetails.PROTOBUF_ERROR));
            TestProto.S2C_Login.Builder result = TestProto.S2C_Login.newBuilder();
            result.setStatus(false);
            result.setMsg(OptionDetails.PROTOBUF_ERROR.getMsg());
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_LOGIN);
        }
        return null;
    }


    @Consumer
    @PostMapping("/giveUp")//放弃任务接口
    public byte[] giveUp(@RequestBody byte[] data)
    {
        byte[] temp = protocolUtil.decodeProtocol(data);
        if (temp == null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.LOGIN, OptionDetails.PROTOBUF_ERROR));
            TestProto.S2C_Login.Builder result = TestProto.S2C_Login.newBuilder();
            result.setStatus(false);
            result.setMsg(OptionDetails.PROTOBUF_ERROR.getMsg());
            byte[] bytes = result.buildPartial().toByteArray();
            return protocolUtil.encodeProtocol(bytes, bytes.length, TestProto.Types.S2C_LOGIN);
        }
        return null;
    }
}
