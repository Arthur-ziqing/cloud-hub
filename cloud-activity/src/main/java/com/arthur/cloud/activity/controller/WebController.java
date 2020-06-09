package com.arthur.cloud.activity.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arthur.cloud.activity.model.User;
import com.arthur.cloud.activity.service.UserService;
import com.arthur.cloud.activity.service.WeChatService;
import com.arthur.cloud.activity.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("web")
public class WebController {


    private static final Logger logger = LoggerFactory.getLogger(WebController.class);


    @Resource
    private WeChatService weChatService;


    @Resource
    @Lazy
    private UserService userService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

//    /**
//     * 小程序授权登录
//     */
//    @ApiOperation(value = "登陆校验")
//    @PostMapping(value = "/login")
//    @ResponseBody
//    public Map<String, Object> miniLogin(String code) {
//        JSONObject jsonObject = weChatService.getWxSession(code);
//        return null;
//    }

    @PostMapping("/updateUserInfo")
    public void updateUserInfo(@RequestBody User user) {
        weChatService.updateUserInfo(user);
    }


//    @ApiOperation(value = "登陆校验")
//    @PostMapping(value = "login")
//    public CommonResult login(@RequestBody Map map) {
//
//        try {
//            String openId = map.get("openId");
//            User users = new User();
//            users.setOpenId(openId);
//            User user = userService.queryOne(users);
//            if (user.getOpenId().equals(openId)) {
//
//                userService.update(user);
//                return new CommonResult(200, "Login success", JWTUtil.sign(openId, user.getNickname()));
//
//            } else {
//
//                throw new UnauthorizedException();
//            }
//
//        } catch (Exception e) {
//            return new CommonResult(401, "Login error", null);
//
//        }
//    }


    @ApiResponses({
            @ApiResponse(code = 404, message = "服务器未找到资源"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 500, message = "服务器错误"),
            @ApiResponse(code = 401, message = "没有访问权限"),
            @ApiResponse(code = 403, message = "服务器拒绝访问"),
    })
    @ApiOperation(value = "小程序登录", httpMethod = "POST", notes = "小程序登录")
    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> doLogin(@ApiParam(required = true, value = "临时登录凭证code", name = "code") String code,
                                       @ApiParam(required = true, value = "用户非敏感信息", name = "rawData")
                                       @RequestParam(value = "rawData", required = true) String rawData,
                                       @ApiParam(required = true, value = "签名", name = "signature")
                                       @RequestParam(value = "signature", required = true) String signature,
                                       @ApiParam(required = true, value = "用户敏感信息", name = "encrypteData")
                                       @RequestParam(value = "encrypteData", required = true) String encrypteData,
                                       @ApiParam(required = true, value = "解密算法的向量", name = "iv")
                                       @RequestParam(value = "iv", required = true) String iv) {
        logger.info("Start get SessionKey");


        Map<String, Object> map = new HashMap<String, Object>();
        logger.info("用户非敏感信息" + rawData);

        JSONObject rawDataJson = JSON.parseObject(rawData);

        logger.info("签名" + signature);
        JSONObject sessionKeyOpenId = weChatService.getWxSession(code);
        logger.info("post请求获取的SessionAndopenId=" + sessionKeyOpenId);

        String openid = sessionKeyOpenId.getString("openid");

        String sessionKey = sessionKeyOpenId.getString("session_key");

        logger.info("openid=" + openid + ",session_key=" + sessionKey);

        User user = userService.queryByID(openid);
        //uuid生成唯一key
        String skey = UUID.randomUUID().toString();
        if (user == null) {
            //入库
            String nickName = rawDataJson.getString("nickName");
            String avatarUrl = rawDataJson.getString("avatarUrl");
            String gender = rawDataJson.getString("gender");
            String city = rawDataJson.getString("city");
            String country = rawDataJson.getString("country");
            String province = rawDataJson.getString("province");


            user = new User();
            user.setOpenId(openid);
            user.setCreatetime(new Date());
            user.setNickname(nickName);
            user.setCountry(country);
            user.setCity(city);
            user.setProvince(province);
            user.setAvatarUrl(avatarUrl);
            user.setGender(Integer.parseInt(gender));
            user.setUpdatetime(new Date());
            user.setSessionKey(sessionKey);
            user.setSkey(skey);
            userService.insert(user);
        } else {
            //已存在
            logger.info("用户openid已存在,不需要插入");
        }
        //根据openid查询skey是否存在
        String skey_redis = redisTemplate.opsForValue().get(openid);
        if (StringUtils.isNotBlank(skey_redis)) {
            //存在 删除 skey 重新生成skey 将skey返回
            redisTemplate.delete(skey_redis);

        }
        //  缓存一份新的
        JSONObject sessionObj = new JSONObject();
        sessionObj.put("openId", openid);
        sessionObj.put("sessionKey", sessionKey);
        redisTemplate.opsForValue().set(skey, sessionObj.toJSONString());
        redisTemplate.opsForValue().set(openid, skey);

        //把新的sessionKey和oppenid返回给小程序
        map.put("skey", skey);
        map.put("result", "0");


        JSONObject userInfo = WXBizDataCrypt.decrypt1(encrypteData, sessionKey, iv);
        logger.info("根据解密算法获取的userInfo=" + userInfo);
        assert userInfo != null;
        userInfo.put("balance", 0);
        map.put("userInfo", userInfo);
        return map;
    }


    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @GetMapping(value = "currentUser")
    public CommonResult getCurrentUser(HttpServletRequest request) {

        try {

            User users = JWTUtil.getToken(request);
            users = userService.queryOne(users);
            return new CommonResult(200, "Login success", users);
        } catch (Exception e) {

            return new CommonResult(401, "Login error", null);
        }

    }

    @PostMapping(value = "autoLogin")
    public CommonResult autoLogin(HttpServletRequest request, String code, Model model) {

        String json = HttpRequestUtils.sendGet(WechatAccessUtils.GET_OPEN_ID_URL,
                "appid=" + WechatAccessUtils.APPID +
                        "&secret=" + WechatAccessUtils.SECRET +
                        "&code=" + code + "&grant_type=authorization_code");
        String json2 = HttpRequestUtils.sendGet(WechatAccessUtils.GET_ACCESS_TOKEN_URL,
                "grant_type=client_credential&appid=" +
                        WechatAccessUtils.APPID +
                        "&secret=" + WechatAccessUtils.SECRET);
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject jsonObject2 = JSONObject.parseObject(json2);
        String openId = (String) jsonObject.get("openid");
        String accessToken = (String) jsonObject2.get("access_token");
        String jsoninfo = HttpRequestUtils.sendGet(WechatAccessUtils.GET_USERINFO_URL, "access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN");
        JSONObject jsonObjectinfo = JSONObject.parseObject(jsoninfo);
        String headimgUrl = (String) jsonObjectinfo.get("headimgurl");
        User user = new User();
        user.setOpenId(openId);

        try {
            if (user.getOpenId() != null) {
                user = userService.queryOne(user);
                if (user == null) {
                    return new CommonResult(401, "Login error", openId);
                } else {
                    return new CommonResult(200, "Login success", JWTUtil.sign(user.getOpenId(), user.getNickname()));
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
        return null;
    }

    @GetMapping("/article")
    public CommonResult article() {

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new CommonResult(200, "You are already logged in", null);
        } else {
            return new CommonResult(200, "You are guest", null);
        }
    }

    @GetMapping("getToken")
    public CommonResult getToken() {

        return new CommonResult(200, "You are guest", WechatAccessUtils.getToken());

    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public CommonResult requireAuth() {

        return new CommonResult(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public CommonResult requireRole() {

        return new CommonResult(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public CommonResult requirePermission() {

        return new CommonResult(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult unauthorized() {

        return new CommonResult(401, "Unauthorized", null);
    }

}