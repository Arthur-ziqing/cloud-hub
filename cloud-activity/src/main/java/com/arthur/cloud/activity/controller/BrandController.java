package com.arthur.cloud.activity.controller;

import com.arthur.cloud.activity.model.Activity;
import com.arthur.cloud.activity.model.Brand;
import com.arthur.cloud.activity.model.condition.PageCondition;
import com.arthur.cloud.activity.model.vo.BrandVo;
import com.arthur.cloud.activity.service.BrandService;
import com.arthur.cloud.activity.util.CommonResult;
import com.arthur.cloud.activity.util.PageAjax;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 品牌
 * @create 2020-06-17 11:51
 * @Version 1.0
 **/
@CrossOrigin
@RestController
@RequestMapping("brand")
public class BrandController {


    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Resource
    private BrandService brandService;


    @ApiOperation(value = "品牌数据接入更新接口", httpMethod = "POST", notes = "品牌数据接入更新接口")
    @PostMapping("saveOrUpdate")
    public CommonResult save(@ModelAttribute BrandVo brandVo) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandVo, brand);
        if (brandVo.getId() == null) {
            brand.setUpdater(brandVo.getOperator());
            brand.setCreateTime(new Date());
            brand.setUpdater(brandVo.getOperator());
            brand.setUpdateTime(new Date());
            return brandService.save(brand);
        } else {
            brand.setUpdater(brandVo.getOperator());
            brand.setUpdateTime(new Date());
            return brandService.update(brand);
        }
    }

    @ApiOperation(value = "品牌数据分页查询", httpMethod = "GET", notes = "品牌数据分页查询")
    @GetMapping("/queryByPage")
    public PageAjax<Brand> queryByPage(@ModelAttribute PageCondition condition){
        PageAjax<Brand> pageAjax = new PageAjax<>();
        BeanUtils.copyProperties(condition,pageAjax);
        Example example = new Example(Brand.class);
        pageAjax = brandService.queryByPage(pageAjax,example);
        return pageAjax;
    }

    @ApiOperation(value = "品牌数据删除", notes = "品牌数据删除")
    @DeleteMapping("{id}")
    public CommonResult delete(@PathVariable Integer id){
        return brandService.delete(id);
    }
}
