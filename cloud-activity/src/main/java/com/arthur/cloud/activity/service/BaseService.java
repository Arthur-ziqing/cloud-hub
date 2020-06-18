package com.arthur.cloud.activity.service;

import com.arthur.cloud.activity.util.CommonResult;
import com.arthur.cloud.activity.util.AppUtil;
import com.arthur.cloud.activity.util.PageAjax;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 通用接口,所有Service需要继承这个接口
 * @param <T>
 */
@Service
@Transactional(rollbackFor = Exception.class)
public abstract class BaseService<T> {

    @Autowired
    protected Mapper<T> mapper;

    @Autowired
    protected MySqlMapper<T> mySqlMapper;

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

    public PageAjax<T> queryByPage(PageAjax<T> page, Example example){
        RowBounds rowBounds = new RowBounds(page.getStart() * page.getLimit(), page.getLimit());
        page.setTotal(mapper.selectCountByExample(example));
        page.setList(mapper.selectByExampleAndRowBounds(example,rowBounds));
        return page;
    }

    public CommonResult save(T entity) {
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

    public CommonResult saveNotNull(T entity) {
    	int ret = mapper.insertSelective(entity);
        String result = null;
        if(ret <= 0){
        	result = "更新失败";
        }
    	return AppUtil.returnObj(result);
    }

    public CommonResult update(T entity) {
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


    public CommonResult delete(Object key) {
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

    public int insertList(List<T> list){
        return mySqlMapper.insertList(list);
    }

    public CommonResult saveList(List<T> list) {
        int ret = insertList(list);
        String result = null;
        if(ret <= 0){
            result = "添加失败";
        }
        return AppUtil.returnObj(result);
    }

}
