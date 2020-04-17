package com.arthur.cloud.consumer.service.Handler;

import com.arthur.cloud.common.util.ReturnResult;
import com.arthur.cloud.consumer.service.HelloService;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-04-16 17:16
 * @Version 1.0
 **/
public class HelloHandler implements HelloService {


    @Override
    public ReturnResult saveHello() {
        return ReturnResult.build(400,"系统异常");
    }
}
