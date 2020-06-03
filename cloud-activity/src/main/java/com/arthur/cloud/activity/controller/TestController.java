package com.arthur.cloud.activity.controller;

import com.arthur.cloud.activity.service.UsersService;
import com.arthur.cloud.activity.utils.CommonResult;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-06-03 16:02
 * @Version 1.0
 **/
@RestController
public class TestController {



    @Resource
    private UsersService usersService;

    @GetMapping("/test")
    public String test(){
        return "test failed";
    }

    @GetMapping("/test1")
    public CommonResult test1(){
        return new CommonResult(usersService.queryAll());
    }
}
