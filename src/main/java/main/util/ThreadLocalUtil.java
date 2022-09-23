package main.util;

import com.mysql.cj.Session;

public class ThreadLocalUtil {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();

    /**
     * 添加当前登录用户方法  在拦截器方法执行前调用设置获取用户
     * @param user
     */
    public static void addCurrentUser(User user){
        userThreadLocal.set(user);
    }

    /**
     * 获取当前登录用户方法
     */
    public static User getCurrentUser(){
        return userThreadLocal.get();
    }


    /**
     * 删除当前登录用户方法  在拦截器方法执行后 移除当前用户对象
     */
    public static void remove(){
        userThreadLocal.remove();
    }

}
