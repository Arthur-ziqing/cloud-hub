package com.arthur.cloud.activity.model;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -4900248123286980845L;

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
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createtime;

    /**
     * 更新时间
     */
    @Column(name = "updateTime")
    private Date updatetime;

    /**
     * 用户登录态标识
     */
    @Column(name = "skey")
    private String skey;


    /**
     * sessionKey
     */
    @Column(name = "sessionKey")
    private String sessionKey;

}