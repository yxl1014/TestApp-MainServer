package main.zyb.service;

public interface ConsumerService {
    /**
     * 生产者和消费者公用逻辑、获取任务信息
     */
    public byte[] getTask(byte[] data);
    /**
     * 生产者获取发布的所有任务的详细信息
     */
    public byte[] prod_GetAddTasks(byte[] data);


}
