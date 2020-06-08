package com.arthur.cloud.activity.service;


import com.arthur.cloud.activity.mapper.UserMapper;
import com.arthur.cloud.activity.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService extends BaseService<User>{

    @Resource
    @Lazy
    private UserMapper userMapper;

    public User getUser(User user) {

        user = userMapper.selectOne(user);
        return user;
    }

}