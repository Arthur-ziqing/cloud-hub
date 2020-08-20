package com.arthur.cloud.activity.mapper;

import com.arthur.cloud.activity.model.User;
import com.arthur.cloud.activity.model.condition.PageCondition;
import com.arthur.cloud.activity.model.vo.UserVo;
import com.arthur.cloud.activity.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends MyMapper<User> {

    List<UserVo> queryByInviteId(@Param("condition") PageCondition condition, @Param("inviteOpenId") String inviteOpenId);

    int queryByInviteIdCount(String inviteOpenId);

}