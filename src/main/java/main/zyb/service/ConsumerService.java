package main.zyb.service;

public interface ConsumerService {
    /**
     * 生产者和消费者公用逻辑、获取任务信息
     */
    public byte[] getTask(byte[] data);
    /**
     * 消费者接受任务
     */
    public byte[] cons_TakeTask(byte[] data);
    /**
     * 消费者开始任务
     */
    public byte[] cons_StartTask(byte[] data);
    /**
     * 消费者结束任务
     */
    public byte[] cons_EndTask(byte[] data);
    /**
     * 消费者放弃任务
     */
    public byte[] cons_DelTask(byte[] data);
    /**
     * 消费者获取接受的所有任务的详细信息
     */
    public byte[] cons_AllGetTasks(byte[] data);

}
