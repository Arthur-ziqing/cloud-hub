package com.arthur.cloud.activity.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arthur.cloud.activity.model.User;
import com.arthur.cloud.activity.utils.HttpRequestUtils;
import com.arthur.cloud.activity.utils.WeChatAuthProperties;
import com.arthur.cloud.activity.utils.WechatAccessUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 微信授权登录
 * @create 2020-06-05 17:05
 * @Version 1.0
 **/
@Service
public class WeChatService {

    private static final Logger logger = LoggerFactory.getLogger(WeChatService.class);

    @Resource
    private UserService userService;

    /**
     * 服务器第三方session有效时间，单位秒, 默认1天
     */
    private static final Long EXPIRES = 86400L;

    private RestTemplate wxAuthRestTemplate = new RestTemplate();

    @Resource
    private WeChatAuthProperties wechatAuthProperties;



    /**
     * 微信登录获取oppenId uuid
     * @param code code
     * @return
     */
    public JSONObject getWxSession(String code){
        //微信登录的code值
        String wxCode = code;
        //服务器端调用接口的url
        String requestUrl = wechatAuthProperties.getSessionHost();
        //封装需要的参数信息
        Map<String,String> requestUrlParam = new HashMap<String,String>();
        //开发者设置中的appId
        requestUrlParam.put("appid",wechatAuthProperties.getAppId());
        //开发者设置中的appSecret
        requestUrlParam.put("secret",wechatAuthProperties.getSecret());
        //小程序调用wx.login返回的code
        requestUrlParam.put("js_code", wxCode);
        //默认参数
        requestUrlParam.put("grant_type", wechatAuthProperties.getGrantType());

        JSONObject jsonObject = JSON.parseObject(HttpRequestUtils.sendPost(requestUrl,requestUrlParam));

        String openId = jsonObject.getString("oppenid");
        String unionid = jsonObject.getString("unionid");
        String sessionKey = jsonObject.getString("session_key");
        User user = new User();
        user.setOpenId(openId);
        loginOrRegisterConsumer(user);
        return jsonObject;
    }


    private void loginOrRegisterConsumer(User user) {
        User user1 = userService.queryByID(user.getOpenId());
        if (null == user1) {
            userService.insert(user);
        }
    }


    /**
     * 更新用户信息
     * @param user 用户信息
     */
    public void updateUserInfo(User user){
        User userExist = userService.queryByID(user.getOpenId());
        BeanUtils.copyProperties(user,userExist);
        userService.updateByID(userExist);
    }


}
