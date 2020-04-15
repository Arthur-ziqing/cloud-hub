package com.arthur.cloud.zuul.service;

import com.arthur.cloud.common.pojo.WebUser;
import com.arthur.cloud.common.util.ReturnResult;
import com.arthur.cloud.zuul.service.handler.HystrixWebUserHandler;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "hichat-provider",fallback = HystrixWebUserHandler.class)
public interface WebUserService {


    @RequestMapping(value="/web-user/login",method = RequestMethod.POST)
    ReturnResult<WebUser> login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password);

}
