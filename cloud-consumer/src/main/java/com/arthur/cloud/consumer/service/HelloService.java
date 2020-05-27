package com.arthur.cloud.consumer.service;

import com.arthur.cloud.common.util.ReturnResult;
import com.arthur.cloud.consumer.service.Handler.HelloHandler;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "cloud-provider",fallback = HelloHandler.class)
public interface HelloService {

    @PostMapping("/hello/saveHello")
    ReturnResult saveHello();
}
