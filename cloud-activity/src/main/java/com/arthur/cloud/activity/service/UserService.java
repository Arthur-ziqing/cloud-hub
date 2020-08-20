package com.arthur.cloud.activity.service;


import com.arthur.cloud.activity.mapper.UserMapper;
import com.arthur.cloud.activity.model.User;
import com.arthur.cloud.activity.model.condition.PageCondition;
import com.arthur.cloud.activity.model.vo.UserVo;
import com.arthur.cloud.activity.util.PageAjax;
import com.github.pagehelper.Page;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    public PageAjax<UserVo> queryByInviteId(PageCondition pageCondition, String inviteOpenId) {

        PageAjax<UserVo> pageAjax = new PageAjax<>();
        int count = userMapper.queryByInviteIdCount(inviteOpenId);
        List<UserVo> list = new ArrayList<>();
        if(count>0){
            list = userMapper.queryByInviteId(pageCondition,inviteOpenId);
        }
        pageAjax.setList(list);
        pageAjax.setTotal(count);
        return pageAjax;
    }
}