package com.arthur.cloud.provider.controller;

import com.arthur.cloud.common.util.ReturnResult;
import com.arthur.cloud.provider.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-05-27 11:43
 * @Version 1.0
 **/
@RestController
@RequestMapping("hello")
public class HelloController {

    @Autowired
    private HelloService helloService;


    @PostMapping("saveHello")
    public ReturnResult save(){
        return ReturnResult.ok(helloService.testSelect());
    }
}
