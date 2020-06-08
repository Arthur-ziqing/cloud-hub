package com.arthur.cloud.activity.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arthur.cloud.activity.util.HttpRequestUtils;

import java.util.Random;


public class WechatAccessUtils {


//    @Resource
//    RedisService redisService;


    /**
     * 小程序id
     */
    public static final String APPID = "wx6b654fd60d9bf0b4";

    /**
     * 小程序密钥
     */
    public static final String SECRET = "f9e859308ef42e84f339f2e615093f7f";

    /**
     * auth.code2Session 登录获取openid
     */
    public static final String GET_OPEN_ID_URL = "https://api.weixin.qq.com/sns/jscode2session";

    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";



    /**
     * generate random string for wechat config
     *
     * @param length length of random string
     * @return
     */
    public static String getRandomString(int length) {

        String base = "abcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {

            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();

    }


    /**
     * 获取token
     */
    public static String getToken() {

        //redis中获取
        //如果获取的为空那么请求wechat

        String token = HttpRequestUtils.sendGet(GET_ACCESS_TOKEN_URL, "grant_type=client_credential&appid=" + APPID + "&secret=" + SECRET);
        JSONObject jsonObject = JSON.parseObject(token);
        String string = (String) jsonObject.get("access_token");

        return string;
    }
}
