package main.logs;

/**
 * @author yxl
 * @date: 2022/9/12 下午1:10
 */
public enum OptionDetails {

    //---------------------LoginServer---------------------
    //测试
    TEST_OK("测试成功", "无敌"),

    //自定义协议
    PROTOCOL_ERROR("协议错误", "协议错误"), PROTOBUF_ERROR("PROTO数据错误", "PROTO数据错误"),


    //系统
    PARAM_ERROR("参数异常", "参数异常"), SYSTEM_ERROR("系统错误", "系统错误"),


    //拦截器
    NO_CONTROLLER("拦截器错误", "没有此接口"),NO_TOKEN("拦截器错误","没有携带token"),
    TOKEN_ERROR("拦截器错误","token无效"),TOKEN_EXPIRES("拦截器错误","token已过期"),


    NO_CHECK("拦截器日志", "访问无CHECK接口"), CHECK("拦截器日志", "访问有CHECK接口"),
    CHECK_OK("拦截器日志","token验证成功"),


    //登录
    LOGIN_OK("登录成功", "登录成功"),UPDATE_IP("登录成功","Ip地址发生变化"),
    LOGIN_TEL_PWD_ERROR("登陆失败", "电话或密码错误"), LOGIN_EMAIL_PWD_ERROR("登陆失败", "邮箱或密码错误"),
    LOGIN_TOKEN_ERROR("登录失败","token验证失败"),


    //注册
    REGISTER_TEL_EXIST("注册失败","此电话已存在"), REGISTER_OK("注册成功", "注册成功"),



    //过滤操作
    FILTER_MSG("过滤操作","token不为空"),FILTER_MSG_ERROR("过滤操作","token为空"),

    //身份验证操作
    IDENTITY_administrators("验证操作","管理员访问"),IDENTITY_USER("验证操作","普通用户访问被拦截"),




    //---------------------MainServer---------------------
    //工具类报错
    //Task
    TASK_EXIST("添加任务失败","任务已存在"),TASK_NOT_FOUND("查询任务失败","任务不存在"),
    P_TASK_START_OK("生产者开始任务","生产者开始任务成功"),P_TASK_EXIST("生产者开始任务","任务已经开始"),
    P_TASK_NOT_FOUND("生产者开始任务","任务不存在"),P_TASK_NO_START("生产者结束任务","任务为开始"),

    USER_NOT_FOUND("获取用户信息失败","用户不存在"),PUBLIC_CONDUCT_NOT_FOUND("获取任务进行时失败","任务进行时不存在"),

    ;

    private String status;
    private String msg;

    OptionDetails(String status, String msg) {
        this.status = status;
        this.msg = msg;
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
