package main.logs;

/**
 * @author yxl
 * @date: 2022/9/12 下午1:10
 */
public enum OptionDetails {

    //---------------------LoginServer---------------------
    //测试
    TEST_OK("测试", "成功", "无敌"),

    //自定义协议
    PROTOCOL_ERROR("协议错误", "失败", "协议错误"),
    PROTOBUF_ERROR("PROTO数据错误", "失败", "PROTO数据错误"),


    //系统
    PARAM_ERROR("参数异常", "失败", "参数异常"),
    SYSTEM_ERROR("系统错误", "失败", "系统错误"),


    //拦截器
    NO_CONTROLLER("拦截器错误", "失败", "没有此接口"),
    NO_TOKEN("拦截器错误", "失败", "没有携带token"),
    TOKEN_ERROR("拦截器错误", "失败", "token无效"),
    TOKEN_EXPIRES("拦截器错误", "失败", "token已过期"),


    NO_CHECK("拦截器日志", "成功", "访问无CHECK接口"),
    CHECK("拦截器日志", "成功", "访问有CHECK接口"),
    NO_PRODUCER("拦截器日志", "成功", "访问无Producer接口"),
    NO_Consumer("拦截器日志", "成功", "访问无Consumer接口"),
    PRODUCER("拦截器日志", "成功", "访问有Producer接口"),
    Consumer("拦截器日志", "成功", "访问有Consumer接口"),
    CHECK_OK("拦截器日志", "成功", "token验证成功"),


    //登录
    LOGIN_OK("登录", "成功", "登录成功"),
    UPDATE_IP("登录", "成功", "Ip地址发生变化"),
    LOGIN_TEL_PWD_ERROR("登陆", "失败", "电话或密码错误"),
    LOGIN_EMAIL_PWD_ERROR("登陆", "失败", "邮箱或密码错误"),
    LOGIN_TOKEN_ERROR("登录", "失败", "token验证失败"),


    //注册
    REGISTER_TEL_EXIST("注册", "失败", "此电话已存在"),
    REGISTER_OK("注册", "成功", "注册成功"),


    //过滤操作
    FILTER_MSG("过滤操作", "成功", "token不为空"),
    FILTER_MSG_ERROR("过滤操作", "成功", "token为空"),
    FILTER_MSG_OK("过滤操作", "成功", "token验证通过"),

    //身份验证操作
    IDENTITY_administrators("验证操作", "成功", "管理员访问"),
    IDENTITY_USER("验证操作", "成功", "普通用户访问被拦截"),


    //---------------------MainServer---------------------
    //工具类报错
    //Task
    TASK_EXIST("添加任务", "失败", "任务已存在"),
    TASK_NOT_FOUND("查询任务", "失败", "任务不存在"),
    P_TASK_START_OK("生产者开始任务", "成功", "生产者开始任务成功"),
    P_TASK_EXIST("生产者开始任务", "失败", "任务已经开始"),
    P_TASK_NOT_FOUND("生产者开始任务", "失败", "任务不存在"),
    P_TASK_NO_START("生产者结束任务", "失败", "任务未开始"),

    P_TASK_ADD_OK("生产者发布任务", "成功", "发布成功"),
    P_GET_ADD_TASKS_NO_USER("生产者获取发布的任务", "失败", "该用户不存在"),

    C_TAKE_IS_TAKE("消费者接受任务", "失败", "已接受该任务"),
    C_TAKE_TASK_OK("消费者接受任务", "成功", "接受成功"),
    C_START_NO_TAKE("消费者开始任务", "失败", "没有接受该任务"),
    C_START_TASK_NOT_START("消费者开始任务", "失败", "该任务没有开始"),
    C_START_TASK_OK("消费者接受任务", "成功", "任务开始成功"),
    C_END_TASK_NO_DOING("消费者结束任务", "失败", "没有正在执行的任务"),
    C_END_TASK_OK("消费者结束任务", "成功", "任务结束成功"),
    C_DEL_TASK_NO_EXIST("消费者放弃任务", "失败", "该用户没有接受该任务"),
    C_DEL_TASK_OK("消费者放弃任务", "成功", "任务放弃成功"),

    USER_NOT_FOUND("获取用户信息失败", "失败", "用户不存在"),
    PUBLIC_CONDUCT_NOT_FOUND("获取任务进行时失败", "失败", "任务进行时不存在"),

    //数据接口:PROD
    ACCESS_DATA_INTERFACE_ERROR("访问数据接口", "失败", "访问数据解析错误"),
    ACCESS_DATA_INTERFACE_NULL("访问数据接口", "失败", "访问数据为空"),
    ADD_TASK_USERID_TRUE("添加任务", "成功", "UserId无误，任务添加成功"),
    ADD_TASK_USERID_FLOAT("添加任务", "失败", "UserId错误，添加任务失败"),
    END_TASK_USERID_TRUE("关闭任务", "成功", "数据解析成功,任务关闭成功"),
    END_TASK_USERID_FLOAT("关闭任务", "失败", "数据解析失败,关闭任务失败"),
    PROD_TASK_START_TRUE("开始任务", "成功", "数据解析成功，任务开始成功"),
    PROD_TASK_START_FLOAT("开始任务", "失败", "数据解析失败，任务开始失败"),
    PROD_GetResult_TRUE("获取任务测试结果集", "成功", "数据解析成功，获取结果成功"),
    PROD_GetResult_FLOAT("获取任务测试结果集", "失败", "数据解析失败，获取结果失败"),
    PROD_Get_Task_TRUE("获取任务信息", "成功", "数据解析成功，获取任务信息结果成功"),
    PROD_Get_Task_FLOAT("获取任务信息", "失败", "数据解析失败，获取任务信息结果失败"),
    PROD_GET_ALL_ADD_TASKS_TRUE("获取发布的所有任务的详细信息", "成功", "数据解析成功，获取所有任务信息结果成功"),
    PROD_GET_ALL_ADD_TASKS_FLOAT("获取任务信息", "失败", "数据解析失败，获取所有任务信息结果失败"),

    //CONS_
    CONS_TAKE_TASk_TRUE("消费者接受任务", "成功", "数据解析成功，消费者接受任务成功"),
    CONS_TAKE_TASk_FLOAT("消费者接受任务", "失败", "数据解析失败，消费者接受任务失败"),
    CONS_START_TASk_TRUE("消费者开始任务", "成功", "数据解析成功，消费者开始任务成功"),
    CONS_START_TASk_FLOAT("消费者开始任务", "失败", "数据解析失败，消费者开始任务失败"),
    CONS_END_TASk_TRUE("消费者结束任务", "成功", "数据解析成功，消费者结束任务成功"),
    CONS_END_TASk_FLOAT("消费者结束任务", "失败", "数据解析失败，消费者结束任务失败"),
    CONS_DEL_TASk_TRUE("消费者放弃任务", "成功", "数据解析成功，消费者放弃任务成功"),
    CONS_DEL_TASk_FLOAT("消费者放弃任务", "失败", "数据解析失败，消费者放弃任务失败"),
    CONS_ALL_GET_TASkS_TRUE("消费者获取接受的所有任务的详细信息", "成功", "数据解析成功，消费者获取接受的所有任务的详细信息成功"),
    CONS_ALL_GET_TASkS_FLOAT("消费者放弃任务", "失败", "数据解析失败，消费者获取接受的所有任务的详细信息失败"),


    //KAFKA
    KAFKA_CONSUMER_TOPIC_CLOSE_NO_TOPIC("关闭kafka监听器", "失败", "该topic不存在"),
    KAFKA_CONSUMER_TOPIC_CLOSE_OK("关闭kafka监听器", "成功", "成功"),
    KAFKA_CONSUMER_TOPIC_START_ERROR("启动监听线程","失败","线程启动失败"),
    KAFKA_CONSUMER_TOPIC_START_OK("启动监听线程","成功","线程启动成功"),

    KAFKA_CONSUMER_TOPIC_START_EXIST_OK("启动监听线程","成功","该topic存在,关闭"),

    KAFKA_CONSUMER_TOPIC_GET_EXIST("获取监听线程","成功","该队列已存在,直接返回"),
    KAFKA_CONSUMER_TOPIC_GET_NO_EXIST("获取监听线程","成功","该队列不存在,初始化一个"),

    KAFKA_PRODUCER_CREATE_IS_EXIST("创建producer对象","失败","该对象已存在"),
    KAFKA_PRODUCER_CREATE_SUCCESS("创建producer对象","成功","创建成功"),

    KAFKA_PRODUCER_CLOSE_NOT_EXIST("关闭producer对象","失败","该对象不存在"),
    KAFKA_PRODUCER_CLOSE_SUCCESS("关闭producer对象","成功","关闭成功"),

    KAFKA_PRODUCER_SEND_NO_PRODUCER("发送信息","失败","producer对象不存在"),
    KAFKA_PRODUCER_SEND_SUCCESS("发送信息","成功","发送成功"),
    ;
    private String type;
    private String status;
    private String msg;

    OptionDetails(String type, String status, String msg) {
        this.type = type;
        this.status = status;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAll() {
        return this.status + "---" + this.msg;
    }
}
