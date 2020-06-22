package com.arthur.cloud.activity.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 奖品vo
 * @create 2020-06-17 15:01
 * @Version 1.0
 **/
@Data
@ApiModel
public class PrizeVo implements Serializable {

    private static final long serialVersionUID = -2583183972285523101L;

    private Long id;

    /**
     * 等级名称
     */
    @ApiModelProperty(value = "等级名称",dataType = "String")
    private String levelName;

    /**
     * 奖品
     */
    @ApiModelProperty(value = "奖品",dataType = "String")
    private String levelPrize;

    /**
     * 邀请等级名称
     */
    @ApiModelProperty(value = "邀请等级名称",dataType = "String")
    private String inviteName;

    /**
     * 邀请奖
     */
    @ApiModelProperty(value = "邀请奖",dataType = "String")
    private String invitePrize;

    /**
     * 中奖号码
     */
    @ApiModelProperty(value = "中奖号码",dataType = "Long")
    private Long num;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除",dataType = "boolean")
    private boolean deleted;
}
