package com.arthur.cloud.zuul.service.handler;

import com.arthur.cloud.common.pojo.WebUser;
import com.arthur.cloud.common.util.ReturnResult;
import com.arthur.cloud.zuul.service.WebUserService;
import org.springframework.stereotype.Component;


@Component
public class HystrixWebUserHandler implements WebUserService {


    @Override
    public ReturnResult<WebUser> login(String account, String password) {
        return ReturnResult.build(400,"系统异常");
    }

}
