package com.arthur.cloud.activity.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author 秦梓青
 */
@Data
@Table(name = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 4398103412098229973L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动状态
     */
    private String type;

    /**
     * 封面图片
     */
    @Column(name = "coverImages")
    private String coverImages;

    /**
     * 详情页图片（逗号分割），最多5张
     */
    private String images;

    /**
     * 分享图片
     */
    @Column(name = "shareImage")
    private String shareImage;

    /**
     * 品牌id
     */
    @Column(name = "brandId")
    private Long brandId;

    /**
     * 开奖时间
     */
    @Column(name = "openTime")
    private Date openTime;

    /**
     * 幸运奖数量
     */
    @Column(name = "luckyPrizeNum")
    private Long luckyPrizeNum;

    /**
     * 幸运数字
     */
    @Column(name = "luckyNumber")
    private Long luckyNumber;

    /**
     * 幸运奖名称
     */
    @Column(name = "luckyPrizeName")
    private String luckyPrizeName;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新时间
     */
    @Column(name = "updateTime")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updater;

}