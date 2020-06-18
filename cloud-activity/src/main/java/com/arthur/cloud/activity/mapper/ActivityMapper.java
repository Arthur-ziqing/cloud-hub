package com.arthur.cloud.activity.mapper;

import com.arthur.cloud.activity.model.Activity;
import com.arthur.cloud.activity.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 秦梓青
 */
public interface ActivityMapper extends MyMapper<Activity> {


    /**
     * @param start 开始位置
     * @param limit 分页长度
     * @param openId 用户
     * @return 我参与的活动列表
     */
    List<Activity> queryJoin(@Param("start") Integer start, @Param("limit") Integer limit,@Param("openId") String openId);

    /**
     * @param openId 用户
     * @return 条数
     */
    int queryJoinCount(String openId);
}