package com.arthur.cloud.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.arthur.cloud.activity.model.User;
import com.arthur.cloud.activity.service.UserService;
import com.arthur.cloud.activity.service.WeChatService;
import com.arthur.cloud.activity.util.CommonResult;
import com.arthur.cloud.activity.util.HttpRequestUtils;
import com.arthur.cloud.activity.util.JWTUtil;
import com.arthur.cloud.activity.util.WechatAccessUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("web")
public class WebController {

//    @RequestMapping(value = "sign", method = RequestMethod.GET)
//    public SignVO sign(Model model, String url) {
//
//        return WechatAccessUtils.access(model, url);
//
//    }

    @Resource
    private WeChatService weChatService;

    /**
     * 小程序授权登录
     */
    @ApiOperation(value = "登陆校验")
    @PostMapping(value = "/miniLogin")
    @ResponseBody
    public Map<String, Object> miniLogin(String code) {
        JSONObject jsonObject = weChatService.getWxSession(code);
        return null;
    }

    @PostMapping("/updateUserInfo")
    public void updateUserInfo(@RequestBody User user) {
        weChatService.updateUserInfo(user);
    }

    @Resource
    @Lazy
    private UserService userService;


    @ApiOperation(value = "登陆校验")
    @PostMapping(value = "login")
    public CommonResult login(@RequestBody Map map) {

        try {
            String openId = (String) map.get("openId");
            User users = new User();
            users.setOpenId(openId);
            User user = userService.queryOne(users);
            if (user.getOpenId().equals(openId)) {

                userService.update(user);
                return new CommonResult(200, "Login success", JWTUtil.sign(openId, user.getNickname()));

            } else {

                throw new UnauthorizedException();
            }

        } catch (Exception e) {
            return new CommonResult(401, "Login error", null);

        }
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