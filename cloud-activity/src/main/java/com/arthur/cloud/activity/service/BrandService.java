package com.arthur.cloud.activity.service;

import com.arthur.cloud.activity.mapper.BrandMapper;
import com.arthur.cloud.activity.model.Brand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 品牌业务
 * @create 2020-06-17 11:50
 * @Version 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class BrandService extends BaseService<Brand>{

    @Resource
    private BrandMapper brandMapper;



}
