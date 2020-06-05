package com.arthur.cloud.activity.config;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-06-05 17:03
 * @Version 1.0
 **/
public class AppContext implements AutoCloseable {


    private static final ThreadLocal<String> CURRENT_CONSUMER_WECHAT_OPENID = new ThreadLocal<>();

    public AppContext(String wechatOpenid) {
        CURRENT_CONSUMER_WECHAT_OPENID.set(wechatOpenid);
    }
    @Override
    public void close() {
        CURRENT_CONSUMER_WECHAT_OPENID.remove();
    }
    public static String getCurrentUserWechatOpenId() {
        return CURRENT_CONSUMER_WECHAT_OPENID.get();
    }
}