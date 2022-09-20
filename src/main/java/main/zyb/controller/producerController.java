package main.zyb.controller;

import main.annotation.Consumer;
import main.annotation.Producer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")//生产者接口
public class producerController {
    @Producer
    @PostMapping("/release")//发布任务接口
    public byte[] release(@RequestBody byte[] data)
    {
        return null;
    }

    @Producer
    @PostMapping("/Start")//开始任务任务接口
    public byte[] Start(@RequestBody byte[] data)
    {
        return null;
    }

    @Producer
    @PostMapping("/close")//关闭任务接口
    public byte[] close(@RequestBody byte[] data)
    {
        return null;
    }

    @Producer
    @PostMapping("/GetTestResults")//获取测试结果接口
    public byte[] GetTestResults(@RequestBody byte[] data)
    {
        return null;
    }

    @Producer
    @PostMapping("/query")//查询任务接口
    public byte[] query(@RequestBody byte[] data)
    {
        return null;
    }
    @Producer
    @PostMapping("/queryUser")//查询用户任务信息接口
    public byte[] queryUser(@RequestBody byte[] data)
    {
        return null;
    }
}
