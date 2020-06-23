package com.arthur.cloud.activity.controller;

import com.arthur.cloud.activity.exception.BusinessException;
import com.arthur.cloud.activity.model.Activity;
import com.arthur.cloud.activity.model.User;
import com.arthur.cloud.activity.model.condition.PageCondition;
import com.arthur.cloud.activity.model.condition.UserActivityCondition;
import com.arthur.cloud.activity.model.vo.ActivityVo;
import com.arthur.cloud.activity.model.vo.UserActivityVo;
import com.arthur.cloud.activity.service.ActivityService;
import com.arthur.cloud.activity.service.PrizeService;
import com.arthur.cloud.activity.util.CommonResult;
import com.arthur.cloud.activity.util.JWTUtil;
import com.arthur.cloud.activity.util.PageAjax;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 活动
 * @create 2020-06-17 11:30
 * @Version 1.0
 **/
@CrossOrigin
@RestController
@RequestMapping("/activity")
public class ActivityController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Resource
    private ActivityService activityService;


    @ApiOperation(value = "活动数据接入更新", httpMethod = "POST", notes = "活动数据接入更新")
    @PostMapping("/saveOrUpdate")
    public CommonResult save(ActivityVo activityVo){
        try {
            activityService.saveOrUpdate(activityVo);
        }catch (Exception e){
            logger.info("error",e);
            return new CommonResult("操作失败");
        }
        return new CommonResult();
    }


    @ApiOperation(value = "活动列表分页查询", httpMethod = "GET", notes = "活动列表分页查询")
    @GetMapping("/queryByPage")
    @RequiresAuthentication
    public CommonResult queryByPage(PageCondition pageCondition){
        PageAjax<Activity> pageAjax = new PageAjax<>();
        BeanUtils.copyProperties(pageCondition,pageAjax);
        Example example = new Example(Activity.class);
        pageAjax = activityService.queryByPage(pageAjax,example);
        return new CommonResult(pageAjax);
    }

    @ApiOperation(value = "活动删除", notes = "活动删除")
    @DeleteMapping("{id}")
    public CommonResult delete(@PathVariable Long id){
        try {
            activityService.delete(id);
        }catch (Exception e){
            logger.info("error",e);
            return new CommonResult("操作失败");
        }
        return new CommonResult();
    }

    @ApiOperation(value = "用户端活动分页", httpMethod = "GET", notes = "用户端活动分页")
    @GetMapping("/appPage")
    public CommonResult queryByPageAndType(UserActivityCondition condition, HttpServletRequest request) {
        CommonResult result = new CommonResult();
        try {
            User users = JWTUtil.getToken(request);
            PageAjax<UserActivityVo> pageAjax = activityService.queryPageByType(condition,users.getOpenId());
            result.setData(pageAjax);
            return result;
        }catch (BusinessException e){
            logger.info("error",e);
            result.setHasError(true);
            result.setMsg(e.getMessageKey());
            result.setCode(e.getErrorCode());
            return result;
        }
    }

}
