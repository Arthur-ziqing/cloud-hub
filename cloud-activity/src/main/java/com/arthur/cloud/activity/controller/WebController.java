package com.arthur.cloud.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.arthur.cloud.activity.exception.BusinessException;
import com.arthur.cloud.activity.model.User;
import com.arthur.cloud.activity.model.condition.PageCondition;
import com.arthur.cloud.activity.model.vo.UserVo;
import com.arthur.cloud.activity.service.UserService;
import com.arthur.cloud.activity.service.WeChatService;
import com.arthur.cloud.activity.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("web")
public class WebController {


    private static final Logger logger = LoggerFactory.getLogger(WebController.class);


    @Resource
    private WeChatService weChatService;


    @Resource
    private UserService userService;


    @ApiOperation(value = "小程序登录", httpMethod = "GET", notes = "小程序登录")
    @ResponseBody
    @GetMapping("/login")
    public CommonResult doLogin(@ApiParam(required = true, value = "临时登录凭证code", name = "code")
                                @RequestParam(value = "code") String code,
                                @ApiParam(value = "邀请人code", name = "inviteCode")
                                @RequestParam(value = "inviteCode", required = false) String inviteCode) {
        logger.info("Start get SessionKey");
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject sessionKeyOpenId = weChatService.getWxSession(code);
        logger.info("post请求获取的Session和openId=" + sessionKeyOpenId);
        String openid = sessionKeyOpenId.getString("openid");
        String sessionKey = sessionKeyOpenId.getString("session_key");
        logger.info("openid=" + openid + ",session_key=" + sessionKey);
        User user = new User();
        user.setOpenId(openid);
        user = userService.getUser(user);
        if (user == null) {
            user = new User();
            user.setOpenId(openid);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setSessionKey(sessionKey);
            if (inviteCode != null) {
                user.setInviteOpenId(inviteCode);
            }
            userService.insert(user);
        } else {
            //已存在
            logger.info("用户openid已存在,不需要插入");
        }
        //返回token
        map.put("token", JWTUtil.sign(user.getOpenId(), user.getSessionKey()));
        return new CommonResult(map);
    }


    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @ApiOperation(value = "获取用户信息", httpMethod = "GET", notes = "获取用户信息")
    @GetMapping(value = "currentUser")
    public CommonResult getCurrentUser(HttpServletRequest request) {

        try {

            User users = JWTUtil.getToken(request);
            users = userService.queryOne(users);
            return new CommonResult(false, "Login success", users);
        } catch (Exception e) {

            return new CommonResult(true, "Login error", null);
        }

    }

    @ApiOperation(value = "获取用户信息", httpMethod = "POST", notes = "获取用户信息")
    @PostMapping(value = "updateInfo")
    public CommonResult autoLogin(HttpServletRequest request, @RequestBody User rawData) {

        CommonResult result;
        logger.info("用户非敏感信息" + rawData.toString());
        User user;
        try {
            user = JWTUtil.getToken(request);
        } catch (BusinessException e) {
            logger.info("error", e);
            result = new CommonResult(e.getErrorCode(), e.getMessage());
            result.setHasError(true);
            return result;
        }
        String openId = user.getOpenId();
        logger.info("当前登录openID" + user.getOpenId());
        user = userService.getUser(user);
        //入库
        BeanUtils.copyProperties(rawData, user);
        user.setOpenId(openId);
        user.setUpdateTime(new Date());
        return userService.update(user);
    }

    /*@GetMapping("/article")
    public CommonResult article() {

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new CommonResult(false, "You are already logged in", null);
        } else {
            return new CommonResult(false, "You are guest", null);
        }
    }

    @GetMapping("getToken")
    public CommonResult getToken() {

        return new CommonResult(false, "You are guest", WechatAccessUtils.getToken());

    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public CommonResult requireAuth() {

        return new CommonResult(false, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public CommonResult requireRole() {

        return new CommonResult(false, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public CommonResult requirePermission() {

        return new CommonResult(false, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/true")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult unauthorized() {

        return new CommonResult(true, "Unauthorized", null);
    }*/




    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @ApiOperation(value = "获取用户信息", httpMethod = "GET", notes = "获取用户信息")
    @GetMapping(value = "inviteUser")
    public CommonResult in(HttpServletRequest request, PageCondition pageCondition) {

        try {

            User users = JWTUtil.getToken(request);
            PageAjax<UserVo> userList = userService.queryByInviteId(pageCondition, users.getOpenId());
            return new CommonResult(false, "Login success", userList);
        } catch (Exception e) {

            return new CommonResult(true, "Login error", null);
        }

    }


}