package main.logs;

/**
 * @author yxl
 * @date: 2022/9/12 下午12:47
 */

public enum LogMsg {

    //---------------------LoginServer---------------------
    //测试
    TEST("测试一下"),
    LOGIN("登录操作"), REGISTER("注册操作"),INTERCEPTOR("拦截器"),
    FILTER("过滤操作"),
    IDENTITY("验证操作"),
    //---------------------MainServer---------------------
    UTIL("工具类"),MYSQL("数据库"),PUBLIC_CONTEXT("核心逻辑区"),
    ACCESS_DATA_INTERFACE("访问数据接口操作"),KAFKA("KAFKA"),
    ;


    private String name;

    LogMsg(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
