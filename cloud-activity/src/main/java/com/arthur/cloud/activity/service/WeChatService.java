package com.arthur.cloud.activity.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arthur.cloud.activity.config.AppContext;
import com.arthur.cloud.activity.model.User;
import com.arthur.cloud.activity.utils.HttpRequestUtils;
import com.arthur.cloud.activity.utils.WeChatAuthProperties;
import com.arthur.cloud.activity.utils.WechatAccessUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private UserService userService;

    /**
     * 服务器第三方session有效时间，单位秒, 默认1天
     */
    private static final Long EXPIRES = 86400L;

    private RestTemplate wxAuthRestTemplate = new RestTemplate();

    @Autowired
    private WeChatAuthProperties wechatAuthProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


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


    public String create3rdSession(String wxOpenId, String wxSessionKey, Long expires) {
        String thirdSessionKey = WechatAccessUtils.getRandomString(64);
        StringBuffer sb = new StringBuffer();
        sb.append(wxSessionKey).append("#").append(wxOpenId);

        stringRedisTemplate.opsForValue().set(thirdSessionKey, sb.toString(), expires, TimeUnit.SECONDS);
        return thirdSessionKey;
    }


    public void updateUserInfo(User user){
        User userExist = userService.queryByID(AppContext.getCurrentUserWechatOpenId());
        userExist.setAddress(1L);
        userExist.setUpdatedAt(System.currentTimeMillis());
        userExist.setGender(consumer.getGender());
        userExist.setAvatarUrl(consumer.getAvatarUrl());
        userExist.setWechatOpenid(consumer.getWechatOpenid());
        userExist.setEmail(consumer.getEmail());
        userExist.setNickname(consumer.getNickname());
        userExist.setPhone(consumer.getPhone());
        userExist.setUsername(consumer.getUsername());
        consumerMapper.updateConsumer(consumerExist);
    }


}
