package main.zyb.service;

import pto.TestProto;

public interface ProducerService {//生产者服务
    /**
     * 任务发布者开始任务
     */
    public byte[] prod_StartTask(byte[] data);

    /**
     * 任务发布者关闭任务
     */
    public byte[] prod_EndTask(byte[] data);
    /**
     * 生产者添加任务
     */
    public byte[] prod_AddTask(byte[] data);
    /**
     * 生产者获取任务测试结果集
     */
    public byte[] prod_GetResult(byte[] data);
    /**
     * 生产者和消费者公用逻辑、获取任务信息
     */
    public byte[] getTask(byte[] data);

}
