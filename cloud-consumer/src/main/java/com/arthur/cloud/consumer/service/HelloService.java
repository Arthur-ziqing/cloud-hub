package com.arthur.cloud.consumer.service;

import com.arthur.cloud.common.util.ReturnResult;
import com.arthur.cloud.consumer.service.Handler.HelloHandler;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "cloud-provider",fallback = HelloHandler.class)
public interface HelloService {

    ReturnResult saveHello();
}
