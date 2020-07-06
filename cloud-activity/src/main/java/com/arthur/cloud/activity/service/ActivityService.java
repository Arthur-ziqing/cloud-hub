package com.arthur.cloud.activity.service;

import com.arthur.cloud.activity.mapper.ActivityMapper;
import com.arthur.cloud.activity.mapper.BrandMapper;
import com.arthur.cloud.activity.mapper.PrizeMapper;
import com.arthur.cloud.activity.mapper.UJoinAMapper;
import com.arthur.cloud.activity.model.*;
import com.arthur.cloud.activity.model.condition.UserActivityCondition;
import com.arthur.cloud.activity.model.enums.ActivityEnums;
import com.arthur.cloud.activity.model.enums.PrizeEnums;
import com.arthur.cloud.activity.model.enums.UserActivityEnum;
import com.arthur.cloud.activity.model.vo.*;
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

    @Resource
    private UserService userService;


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


    /**
     * 活动开奖
     * @return 返回活动获奖名单
     */
    public ActivityOpenPrizeVo openPrize(Long id, String openId){


        //获取所有参与用户的参与号码
        UJoinA uJoinA = new UJoinA();
        uJoinA.setActivityId(id);
        List<UJoinA> uJoinAList = uJoinAMapper.select(uJoinA);

        //获取当前活动所有的奖项
        Prize prize = new Prize();
        prize.setActivityId(id);
        List<Prize> prizes = prizeMapper.select(prize);

        //获取活动信息
        Activity activity = new Activity();
        activity.setId(id);
        activity = activityMapper.selectByPrimaryKey(id);

        //随机抽取以活动奖项为数量的号码
        List<UJoinA> win = uJoinAList.isEmpty() ? new ArrayList<>() : prizeDraw(uJoinAList, (long) prizes.size());


        //用于判断当前用户是否中活动奖项
        boolean isWin = false;
        boolean isInivte =false;
        //将活动奖项获奖名单数据封装并更新奖项中奖号码
        List<WinPrizeVo> wins = new ArrayList<>();
        for (int i = 0; i < win.size(); i++) {
            UJoinA u = win.get(i);
            Prize p = prizes.get(i);
            WinPrizeVo winPrizeVo = new WinPrizeVo();
            winPrizeVo.setNum(u.getNumber());
            winPrizeVo.setPrizeType(PrizeEnums.ACITICITY);
            winPrizeVo.setPrizeLevelName(p.getLevelName());
            winPrizeVo.setPrizeName(p.getLevelPrize());

            isWin = u.getOpenid().equals(openId);
            User user = new User();
            user.setOpenId(u.getOpenid());
            user = userService.getUser(user);
            winPrizeVo.setNickName(user.getNickname());
            winPrizeVo.setAvatarUrl(user.getAvatarUrl());


            wins.add(winPrizeVo);

            //判断是否被邀请，如果是被邀请，及给到邀请者一个邀请奖
            if(null != user.getInviteOpenId()){
                isInivte = user.getInviteOpenId().equals(openId);
                user.setOpenId(user.getInviteOpenId());
                user = userService.getUser(user);
                //获取邀请奖获取者
                WinPrizeVo invite = new WinPrizeVo();
                invite.setPrizeLevelName(p.getInviteName());
                invite.setPrizeName(p.getInvitePrize());
                invite.setAvatarUrl(user.getAvatarUrl());
                invite.setNickName(user.getNickname());
                invite.setPrizeType(PrizeEnums.INVITE);
                wins.add(invite);
            }

            //更新活动奖项中奖号码
            p.setNum(u.getNumber());
            prizeMapper.updateByPrimaryKey(p);
            uJoinAList.remove(u);
        }




        List<UJoinA> luck = uJoinAList.isEmpty() ? new ArrayList<>() :prizeDraw(uJoinAList,activity.getLuckyNumber());


        boolean isLuck = false;
        //幸运奖
        List<WinPrizeVo> luckWins = new ArrayList<>();
        for (UJoinA item : luck) {
            WinPrizeVo winPrizeVo = new WinPrizeVo();
            winPrizeVo.setNum(item.getNumber());
            winPrizeVo.setPrizeType(PrizeEnums.LUCK);
            winPrizeVo.setPrizeName(activity.getLuckyPrizeName());

            isLuck = item.getOpenid().equals(openId);
            User user = new User();
            user.setOpenId(item.getOpenid());
            user = userService.getUser(user);

            winPrizeVo.setNickName(user.getNickname());
            winPrizeVo.setAvatarUrl(user.getAvatarUrl());
            item.setWinLuck(true);
            luckWins.add(winPrizeVo);
            uJoinAMapper.updateByPrimaryKey(item);
        }

        ActivityOpenPrizeVo winInfo = new ActivityOpenPrizeVo();
        winInfo.setWin(isWin || isInivte || isLuck);
        winInfo.setWins(wins);
        winInfo.setLuckWins(luckWins);
        return  winInfo;
    }

    /**
     * 抽奖
     * @param list 参与者
     * @param n 抽奖次数
     * @return 获奖
     */
    private List<UJoinA> prizeDraw(List<UJoinA> list, Long n){
        List<UJoinA> winNum = new ArrayList<>();
        for (int i = 1; i <= n ; i++) {
            Random random = new Random();
            int index = random.nextInt(list.size());
            list.get(index);
            winNum.add(list.get(index));
            list.remove(index);
        }
        return  winNum;

    }

}
