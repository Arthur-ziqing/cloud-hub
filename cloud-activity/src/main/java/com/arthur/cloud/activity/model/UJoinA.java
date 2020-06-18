package com.arthur.cloud.activity.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 秦梓青
 */
@Table(name = "u_join_a")
@Data
public class UJoinA implements Serializable {

    private static final long serialVersionUID = -4533919838472215282L;

    /**
     * 用户openId
     */
    private String openid;

    /**
     * 活动id
     */
    @Column(name = "activityId")
    private Long activityId;

    /**
     * 中奖号码
     */
    private Long number;

    public UJoinA() {
    }

    public UJoinA(String openid, Long activityId) {
        this.openid = openid;
        this.activityId = activityId;
    }
}