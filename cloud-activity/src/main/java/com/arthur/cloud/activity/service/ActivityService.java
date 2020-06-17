package com.arthur.cloud.activity.service;

import com.arthur.cloud.activity.mapper.ActivityMapper;
import com.arthur.cloud.activity.mapper.PrizeMapper;
import com.arthur.cloud.activity.model.Activity;
import com.arthur.cloud.activity.model.Prize;
import com.arthur.cloud.activity.model.vo.ActivityVo;
import com.arthur.cloud.activity.model.vo.PrizeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 活动业务处理
 * @create 2020-06-17 11:28
 * @Version 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ActivityService extends BaseService<Activity> {

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private PrizeMapper prizeMapper;


    public void saveOrUpdate(ActivityVo vo) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(vo, activity);
        activity.setType(vo.getType().toString());
        activity.setUpdater(vo.getOperator());
        activity.setUpdateTime(new Date());
        List<PrizeVo> prizeVos = vo.getPrizeList();
        if (vo.getId() == null) {
            activity.setCreator(vo.getOperator());
            activity.setCreateTime(new Date());
            activityMapper.insert(activity);
            prizeVos.forEach(item -> {
                Prize prize = new Prize();
                BeanUtils.copyProperties(item, prize);
                prize.setActivityId(activity.getId());
                prizeMapper.insert(prize);
            });
        } else {
            activityMapper.updateByPrimaryKeySelective(activity);
            prizeVos.forEach(item->{
                Prize prize = new Prize();
                BeanUtils.copyProperties(item, prize);
                if (item.isDeleted()){
                    prizeMapper.delete(prize);
                }else {
                    prizeMapper.updateByPrimaryKeySelective(prize);
                }
            });
        }
    }


    public void delete(Long id){
        activityMapper.deleteByPrimaryKey(id);
        prizeMapper.deleteByActivityId(id);
    }

}
