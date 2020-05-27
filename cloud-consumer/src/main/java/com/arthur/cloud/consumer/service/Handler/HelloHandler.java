package com.arthur.cloud.consumer.service.Handler;

import com.arthur.cloud.common.util.ReturnResult;
import com.arthur.cloud.consumer.service.HelloService;
import org.springframework.stereotype.Component;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-04-16 17:16
 * @Version 1.0
 **/
@Component
public class HelloHandler implements HelloService {


    @Override
    public ReturnResult saveHello() {
        return ReturnResult.build(400,"系统异常");
    }
}
