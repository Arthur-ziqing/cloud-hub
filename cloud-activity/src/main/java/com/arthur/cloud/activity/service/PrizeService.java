package com.arthur.cloud.activity.service;

import com.arthur.cloud.activity.mapper.PrizeMapper;
import com.arthur.cloud.activity.model.Prize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 奖品业务
 * @create 2020-06-17 15:19
 * @Version 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class PrizeService extends BaseService<Prize>{

    @Resource
    private PrizeMapper prizeMapper;

    public int deleteByActivityId(Long activityId) {
        return prizeMapper.deleteByActivityId(activityId);
    }
}
