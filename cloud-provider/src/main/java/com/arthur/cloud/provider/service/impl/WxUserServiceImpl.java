package com.arthur.cloud.provider.service.impl;



import com.arthur.cloud.common.pojo.entity.WxUser;
import com.arthur.cloud.provider.mapper.WxUserDao;

import com.arthur.cloud.provider.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * (WxUser)表服务实现类
 *
 * @author makejava
 * @since 2020-05-27 21:22:49
 */
@Service("wxUserService")
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxUserDao wxUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WxUser queryById(Long id) {
        return this.wxUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<WxUser> queryAllByLimit(int offset, int limit) {
        return this.wxUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param wxUser 实例对象
     * @return 实例对象
     */
    @Override
    public WxUser insert(WxUser wxUser) {
        this.wxUserDao.insert(wxUser);
        return wxUser;
    }

    /**
     * 修改数据
     *
     * @param wxUser 实例对象
     * @return 实例对象
     */
    @Override
    public WxUser update(WxUser wxUser) {
        this.wxUserDao.update(wxUser);
        return this.queryById(wxUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.wxUserDao.deleteById(id) > 0;
    }
}