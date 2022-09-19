package main.zyb.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发布任务
 * 接收任务
 * 关闭任务
 * 结束任务
 * 获取测试结果
 * 查询任务
 * 查询用户任务信息
 * 放弃任务
 */
@RestController
@RequestMapping("/main")
public class MainController {

    @PostMapping("/release")//发布任务接口
    public byte[] release(@RequestBody byte[] data)
    {
        return null;
    }

    @PostMapping("/receive")//接收任务接口
    public byte[] receive(@RequestBody byte[] data)
    {
        return null;
    }
    @PostMapping("/close")//关闭任务接口

    public byte[] close(@RequestBody byte[] data)
    {
        return null;
    }

    @PostMapping("/end")//结束任务接口
    public byte[] end(@RequestBody byte[] data)
    {
        return null;
    }

    @PostMapping("/GetTestResults")//获取测试结果接口
    public byte[] GetTestResults(@RequestBody byte[] data)
    {
        return null;
    }

    @PostMapping("/query")//查询任务接口
    public byte[] query(@RequestBody byte[] data)
    {
        return null;
    }

    @PostMapping("/queryUser")//查询用户任务信息接口
    public byte[] queryUser(@RequestBody byte[] data)
    {
        return null;
    }

    @PostMapping("/giveUp")//放弃任务接口
    public byte[] giveUp(@RequestBody byte[] data)
    {
        return null;
    }
}
