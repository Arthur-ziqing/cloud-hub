package com.arthur.cloud.activity.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 邀请人vo
 * @Author 秦梓青
 * @create 2020-08-09 17:05
 */
@ApiModel
public class UserVo implements Serializable {

    private static final long serialVersionUID = 4807778431358691651L;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称",dataType = "String")
    private String nickname;

    /**
     *
     */
    @ApiModelProperty(value = "用户头像",dataType = "String")
    private String avatarUrl;

    @ApiModelProperty(value = "创建时间",dataType = "date")
    private Date createTime;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
