package com.arthur.cloud.activity.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class User implements Serializable {

    private static final long serialVersionUID = -4900248123286980845L;

    @Id
    @Column(name = "openId")
    private String openId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    @Column(name = "avatarUrl")
    private String avatarUrl;

    /**
     * 用户性别；0：未知；1：男；2：女；
     */
    private Integer gender;

    /**
     * 用户所在国家
     */
    private String country;

    /**
     * 用户所在省份
     */
    private String province;

    /**
     * 用户所在城市
     */
    private String city;

    /**
     * 语言
     */
    private String language;


    /**
     * 邀请者 openid
     */
    @Column(name = "inviteOpenId")
    private String inviteOpenId;


    /**
     * sessionKey
     */
    @Column(name = "sessionKey")
    private String sessionKey;


    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "updateTime")
    private Date updateTime;


}