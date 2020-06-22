package com.arthur.cloud.activity.util;

import com.arthur.cloud.activity.exception.BusinessException;
import com.arthur.cloud.activity.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    /**
     * 过期时间设置为20年
     */
    private static final long EXPIRE_TIME = 20 * 365 * 24 * 3600000;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String openId, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("openId", openId)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }


    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("openId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 生成签名
     *
     * @param openId  openId
     * @param nickName     用户昵称
     * @return 加密的token
     */
    public static String sign(String openId, String nickName) {

        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(nickName);
            // 附带username信息
            return JWT.create()
                    .withClaim("openId", openId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }


    public static User getToken(HttpServletRequest request) throws BusinessException {
        Enumeration headerNames = request.getHeaderNames();
        Map<String, String> map = new HashMap<String, String>();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        String token = map.get("authorization");
        if(token == null){
            throw new BusinessException("无授权登录","9999");
        }
        String openId = JWTUtil.getUsername(token);
        if (openId == null ) {
            throw new BusinessException("token 失效","9998");
        }
        User users = new User();
        users.setOpenId(openId);

        return users;
    }
}