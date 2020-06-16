package com.arthur.cloud.activity.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author 秦梓青
 */
@Data
public class Prize {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 活动id
     */
    @Column(name = "artivityId")
    private Long artivityId;

    /**
     * 等级名称
     */
    @Column(name = "levelName")
    private String levelName;

    /**
     * 奖品
     */
    @Column(name = "levelPrize")
    private String levelPrize;

    /**
     * 邀请等级名称
     */
    @Column(name = "inviteName")
    private String inviteName;

    /**
     * 邀请奖
     */
    @Column(name = "invitePrize")
    private String invitePrize;

    /**
     * 中奖号码
     */
    private Long num;
}