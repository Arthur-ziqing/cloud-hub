package com.arthur.cloud.activity.controller;

import com.arthur.cloud.activity.model.Activity;
import com.arthur.cloud.activity.model.condition.PageCondition;
import com.arthur.cloud.activity.model.vo.ActivityVo;
import com.arthur.cloud.activity.service.ActivityService;
import com.arthur.cloud.activity.service.PrizeService;
import com.arthur.cloud.activity.util.CommonResult;
import com.arthur.cloud.activity.util.PageAjax;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @Resource
    private PrizeService prizeService;

    @ApiOperation(value = "新建活动", httpMethod = "POST", notes = "新建活动")
    @PostMapping("/saveOrUpdate")
    public CommonResult save(@ModelAttribute ActivityVo activityVo){
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
    public PageAjax<Activity> queryByPage(@ModelAttribute PageCondition pageCondition){
        PageAjax<Activity> pageAjax = new PageAjax<>();
        BeanUtils.copyProperties(pageCondition,pageAjax);
        PageInfo<Activity> pageInfo = activityService.queryByPage(pageAjax);
        pageAjax.setList(pageInfo.getList());
        pageAjax.setTotal(pageInfo.getTotal());
        return pageAjax;
    }

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
}
