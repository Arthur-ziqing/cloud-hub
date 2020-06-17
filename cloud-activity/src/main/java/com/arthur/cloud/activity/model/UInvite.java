package com.arthur.cloud.activity.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author 秦梓青
 */
@Table(name = "u_invite")
@Data
public class UInvite implements Serializable {

    private static final long serialVersionUID = -1888579393057171705L;

    /**
     * 用户id
     */
    @Column(name = "openId")
    private String openId;

    /**
     * 被邀请者id
     */
    @Column(name = "inviteOpenId")
    private String inviteOpenId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像
     */
    @Column(name = "avatarUrl")
    private String avatarUrl;

    /**
     * 被邀请时间
     */
    @Column(name = "createTime")
    private Date createTime;

}