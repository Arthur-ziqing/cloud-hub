package com.arthur.cloud.provider.controller;

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
    public String save(){
        return "aaa";
    }
}
