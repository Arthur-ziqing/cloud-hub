package com.arthur.cloud.activity.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 用户端活动列表数据封装
 * @create 2020-06-18 13:50
 * @Version 1.0
 **/
@ApiModel(description = "用户端活动列表数据")
@Data
public class UserActivityVo implements Serializable {

    private static final long serialVersionUID = 1823859825020608086L;

    @ApiModelProperty(value = "活动id")
    private Long id;

    /**
     * 活动标题
     */
    @ApiModelProperty(value = "活动标题")
    private String title;

    /**
     * 活动状态
     */
    private String type;

    /**
     * 封面图片
     */
    private String coverImages;

    /**
     * 详情页图片（逗号分割），最多5张
     */
    private String images;

    /**
     * 分享图片
     */
    private String shareImage;
    /**
     * 参加人数
     */
    private Integer joinCount;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 开奖时间
     */
    private Date openTime;

    /**
     * 是否参加
     */
    @ApiModelProperty(value = "开奖时间",dataType = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private boolean isJoin;

    /**
     * 是否中奖
     */
    private boolean isWin;
}
