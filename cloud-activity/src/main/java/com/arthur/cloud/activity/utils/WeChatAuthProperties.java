package com.arthur.cloud.activity.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-06-05 17:07
 * @Version 1.0
 **/
@Component
@Data
public class WeChatAuthProperties {

    @Value("${auth.wechat.sessionHost}")
    private String sessionHost;

    @Value("${auth.wechat.appId}")
    private String appId;

    @Value("${auth.wechat.secret}")
    private String secret;

    @Value("${auth.wechat.grantType}")
    private String grantType;
}
