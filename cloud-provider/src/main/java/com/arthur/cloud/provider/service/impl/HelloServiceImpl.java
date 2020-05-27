package com.arthur.cloud.provider.service.impl;

import com.arthur.cloud.common.pojo.User;
import com.arthur.cloud.provider.mapper.UserMapper;
import com.arthur.cloud.provider.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-05-27 18:05
 * @Version 1.0
 **/
@Service
public class HelloServiceImpl  implements HelloService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> testSelect() {
        return userMapper.testSelect();
    }
}
