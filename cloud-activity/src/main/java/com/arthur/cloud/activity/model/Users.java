package com.arthur.cloud.activity.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * users
 * @author 
 */
@Data
public class Users implements Serializable {
    /**
     * 微信openid
     */
    private String openid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 签名
     */
    private String sign;

    /**
     * 性别 0 女; 1 男；
     */
    private Boolean gender;

    /**
     * 所在地
     */
    private String address;

    /**
     * 学校
     */
    private String school;

    /**
     * 行业
     */
    private String profession;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    private static final long serialVersionUID = 1L;
}