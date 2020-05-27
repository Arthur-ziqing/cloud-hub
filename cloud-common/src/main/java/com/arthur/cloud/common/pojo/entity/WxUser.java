package com.arthur.cloud.common.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (WxUser)实体类
 *
 * @author makejava
 * @since 2020-05-27 21:03:52
 */
@Data
public class WxUser implements Serializable {

    private static final long serialVersionUID = 609551811977759645L;
    
    private Long id;
    /**
    * 小程序用户openid
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
    private Object gender;
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

}