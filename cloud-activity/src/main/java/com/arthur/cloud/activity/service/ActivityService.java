package com.arthur.cloud.activity.service;

import com.arthur.cloud.activity.mapper.ActivityMapper;
import com.arthur.cloud.activity.mapper.BrandMapper;
import com.arthur.cloud.activity.mapper.PrizeMapper;
import com.arthur.cloud.activity.mapper.UJoinAMapper;
import com.arthur.cloud.activity.model.Activity;
import com.arthur.cloud.activity.model.Brand;
import com.arthur.cloud.activity.model.Prize;
import com.arthur.cloud.activity.model.UJoinA;
import com.arthur.cloud.activity.model.condition.UserActivityCondition;
import com.arthur.cloud.activity.model.enums.ActivityEnums;
import com.arthur.cloud.activity.model.enums.UserActivityEnum;
import com.arthur.cloud.activity.model.vo.ActivityVo;
import com.arthur.cloud.activity.model.vo.PrizeLevelVo;
import com.arthur.cloud.activity.model.vo.PrizeVo;
import com.arthur.cloud.activity.model.vo.UserActivityVo;
import com.arthur.cloud.activity.util.PageAjax;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private UJoinAMapper uJoinAMapper;

    @Resource
    private ActivityService activityService;

    @Resource
    private BrandMapper brandMapper;


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
            prizeVos.forEach(item -> {
                Prize prize = new Prize();
                BeanUtils.copyProperties(item, prize);
                if (item.isDeleted()) {
                    prizeMapper.delete(prize);
                } else {
                    prizeMapper.updateByPrimaryKeySelective(prize);
                }
            });
        }
    }


    public void delete(Long id) {
        activityMapper.deleteByPrimaryKey(id);
        prizeMapper.deleteByActivityId(id);
    }


    public PageAjax<UserActivityVo> queryPageByType(UserActivityCondition condition, String openId) {
        PageAjax<UserActivityVo> pageAjax = new PageAjax<>();
        PageAjax<Activity> list;
        if (!UserActivityEnum.JOIN.equals(condition.getType())) {
            list = queryNoJoin(condition);
        } else {
            list = queryJoin(condition, openId);
        }
        List<UserActivityVo> userActivityVos = new ArrayList<>();
        BeanUtils.copyProperties(list, pageAjax);
        if (list.getList() != null) {
            list.getList().forEach(item -> {
                UserActivityVo vo = new UserActivityVo();
                BeanUtils.copyProperties(item, vo);
                if (!UserActivityEnum.JOIN.equals(condition.getType())) {
                    UJoinA uJoinA = new UJoinA(openId, item.getId());
                    uJoinA = uJoinAMapper.selectOne(uJoinA);
                    if (uJoinA != null && item.getType().equals(ActivityEnums.FINISH.toString())) {
                        Long num = uJoinA.getNumber();
                        vo.setJoin(true);
                        Prize prize = new Prize();
                        prize.setActivityId(item.getId());
                        List<Prize> prizes = prizeMapper.select(prize);
                        prizes = prizes.stream().filter(p -> p.getNum().equals(num)).collect(Collectors.toList());
                        vo.setWin(!prizes.isEmpty());
                    } else {
                        vo.setJoin(false);
                        vo.setWin(false);
                    }
                } else {
                    vo.setWin(false);
                    vo.setJoin(true);
                }
                UJoinA uJoinA = new UJoinA();
                uJoinA.setActivityId(item.getId());
                vo.setJoinCount(uJoinAMapper.selectCount(uJoinA));
                List<PrizeLevelVo> levelVos = prizeMapper.queryPrizeLevelByActivity(item.getId());
                vo.setPrizeLevel(levelVos.isEmpty() ? null : levelVos);
                if (item.getBrandId() != null) {
                    Brand brand = new Brand();
                    brand.setId(item.getBrandId());
                    brand = brandMapper.selectOne(brand);
                    if (brand != null) {
                        vo.setBrandName(brand.getBrandName());
                        vo.setBrandLogo(brand.getLogo());
                    }
                }
                userActivityVos.add(vo);
            });
        }
        pageAjax.setList(userActivityVos);
        return pageAjax;
    }


    /**
     * 查询不是我参加的活动
     *
     * @param condition 参数
     * @return 活动分页
     */
    public PageAjax<Activity> queryNoJoin(UserActivityCondition condition) {
        PageAjax<Activity> pageAjax = new PageAjax<>();
        BeanUtils.copyProperties(condition, pageAjax);
        Activity activity = new Activity();
        activity.setType(UserActivityEnum.FINISH.equals(condition.getType()) ? ActivityEnums.FINISH.toString() : ActivityEnums.PROGRESS.toString());
        int count = activityMapper.selectCount(activity);
        Example example = new Example(Activity.class);
        if (count > 0) {
            pageAjax = activityService.queryByPage(pageAjax, example);
        }
        return pageAjax;
    }

    /**
     * 查询我参加的活动
     *
     * @param condition 参数
     * @param openId    用户id
     * @return 活动分页
     */
    public PageAjax<Activity> queryJoin(UserActivityCondition condition, String openId) {
        PageAjax<Activity> pageAjax = new PageAjax<>();
        int count = activityMapper.queryJoinCount(openId);
        List<Activity> list = new ArrayList<>();
        if (count > 0) {
            pageAjax.setList(activityMapper.queryJoin(condition.getStart() * condition.getLimit(), condition.getLimit(), openId));
        }
        pageAjax.setList(list);
        pageAjax.setTotal(count);
        return pageAjax;
    }


}
