package main.logs;

/**
 * @author yxl
 * @date: 2022/9/12 下午12:47
 */

public enum LogMsg {
    //测试
    TEST("测试一下"),
    LOGIN("登录操作"), REGISTER("注册操作"),INTERCEPTOR("拦截器"),
    FILTER("过滤操作"),
    IDENTITY("验证操作")
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
