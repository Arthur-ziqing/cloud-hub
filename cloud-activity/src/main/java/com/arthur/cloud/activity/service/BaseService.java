package com.arthur.cloud.activity.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.arthur.cloud.activity.util.AjaxResult;
import com.arthur.cloud.activity.util.AppUtil;
import com.arthur.cloud.activity.util.PageAjax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 通用接口,所有Service需要继承这个接口
 * @param <T>
 */
@Service
public abstract class BaseService<T> {

    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {

        return mapper;
    }

    public T queryByID(Object key) {
        return mapper.selectByPrimaryKey(key);
    }
    
    public List<T> queryList(T entity){
    	return mapper.select(entity);
    }
    
    public T queryOne(T entity){
    	return mapper.selectOne(entity);
    }
    
    public int queryCount(T entity){
    	return mapper.selectCount(entity);
    }

    public List<T> queryByParams(Object example) {
        return mapper.selectByExample(example);
    }
    
    public List<T> queryAll(){
    	return mapper.selectAll();
    }

    public PageInfo<T> queryByPage(PageAjax<T> page){
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<T> list = mapper.selectAll();
        return new PageInfo<T>(list);
    }

    public AjaxResult save(T entity) {

        int ret = insert(entity);
        String result = null;
        if(ret <= 0){
        	result = "添加失败";
        }
    	return AppUtil.returnObj(result);
    }
    
    public int insert(T entity){
    	return mapper.insert(entity);
    }

    public AjaxResult saveNotNull(T entity) {
    	int ret = mapper.insertSelective(entity);
        String result = null;
        if(ret <= 0){
        	result = "更新失败";
        }
    	return AppUtil.returnObj(result);
    }

    public AjaxResult update(T entity) {
    	int ret = updateByID(entity);
        String result = null;
        if(ret <= 0){
        	result = "更新失败";
        }
    	return AppUtil.returnObj(result);
    }
    
    public int updateByID(T entity){
    	return mapper.updateByPrimaryKeySelective(entity);
    }


    public AjaxResult delete(Object key) {
        int ret = deleteByID(key);
        String result = null;
        if(ret <= 0){
        	result = "删除失败";
        }
    	return AppUtil.returnObj(result);
    }
    
    public int deleteByID(Object key) {
    	return mapper.deleteByPrimaryKey(key);
    }


}
