package main.zyb.controller;

import main.annotation.Consumer;
import main.annotation.Producer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Consumer")
public class ConsumerController {

    @Consumer
    @PostMapping("/receive")//接收任务接口
    public byte[] receive(@RequestBody byte[] data)
    {
        return null;
    }


    @Consumer
    @PostMapping("/Start")//开始任务任务接口
    public byte[] Start(@RequestBody byte[] data)
    {
        return null;
    }


    @Consumer
    @PostMapping("/end")//结束任务接口
    public byte[] end(@RequestBody byte[] data)
    {
        return null;
    }


    @Consumer
    @PostMapping("/query")//查询任务接口
    public byte[] query(@RequestBody byte[] data)
    {
        return null;
    }


    @Consumer
    @PostMapping("/queryUser")//查询用户任务信息接口
    public byte[] queryUser(@RequestBody byte[] data)
    {
        return null;
    }


    @Consumer
    @PostMapping("/giveUp")//放弃任务接口
    public byte[] giveUp(@RequestBody byte[] data)
    {
        return null;
    }
}
