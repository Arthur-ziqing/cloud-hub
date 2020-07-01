package com.arthur.cloud.activity.service;

import com.arthur.cloud.activity.mapper.UJoinAMapper;
import com.arthur.cloud.activity.model.UJoinA;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-06-30 14:17
 * @Version 1.0
 **/
@Service
public class UJoinAService extends BaseService<UJoinA> {

    @Resource
    private UJoinAMapper uJoinAMapper;

}
