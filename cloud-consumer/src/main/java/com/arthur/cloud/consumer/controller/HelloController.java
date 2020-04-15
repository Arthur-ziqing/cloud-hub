package com.arthur.cloud.consumer.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-04-02 17:50
 * @Version 1.0
 **/

@RestController
@RequestMapping("/demo")
public class HelloController {


    @RequestMapping(value = "/hello/{id}",method = RequestMethod.GET)
    public String  getCommentByEssayId(@PathVariable Long id){
        return "hello："+id;
    }

}
