package com.arthur.cloud.provider.mapper;


import com.arthur.cloud.common.pojo.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (WxUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-27 21:24:13
 */
@Mapper
public interface WxUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WxUser queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WxUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param wxUser 实例对象
     * @return 对象列表
     */
    List<WxUser> queryAll(WxUser wxUser);

    /**
     * 新增数据
     *
     * @param wxUser 实例对象
     * @return 影响行数
     */
    int insert(WxUser wxUser);

    /**
     * 修改数据
     *
     * @param wxUser 实例对象
     * @return 影响行数
     */
    int update(WxUser wxUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}