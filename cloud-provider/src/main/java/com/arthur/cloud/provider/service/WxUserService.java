package com.arthur.cloud.provider.service;

import com.arthur.cloud.common.pojo.entity.WxUser;
import java.util.List;

/**
 * (WxUser)表服务接口
 *
 * @author makejava
 * @since 2020-05-27 21:22:49
 */
public interface WxUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WxUser queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WxUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param wxUser 实例对象
     * @return 实例对象
     */
    WxUser insert(WxUser wxUser);

    /**
     * 修改数据
     *
     * @param wxUser 实例对象
     * @return 实例对象
     */
    WxUser update(WxUser wxUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}