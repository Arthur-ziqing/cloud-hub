package com.arthur.cloud.activity.mapper;

import com.arthur.cloud.activity.model.Prize;
import com.arthur.cloud.activity.model.vo.PrizeLevelVo;
import com.arthur.cloud.activity.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author 秦梓青
 */
public interface PrizeMapper extends MyMapper<Prize> {

    /**
     * @param activityId 活动id
     * @return 行数
     */
    int deleteByActivityId(@Param("activityId") Long activityId);

    List<PrizeLevelVo> queryPrizeLevelByActivity(@Param("activityId") Long activityId);
}