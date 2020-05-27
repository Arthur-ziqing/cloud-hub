package com.arthur.cloud.provider.controller;

import com.arthur.cloud.common.util.ReturnResult;
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

    @PostMapping("saveHello")
    public ReturnResult save(){
        return ReturnResult.ok("aaa");
    }
}
