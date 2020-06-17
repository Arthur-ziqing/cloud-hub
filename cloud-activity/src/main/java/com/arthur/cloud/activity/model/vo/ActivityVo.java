package com.arthur.cloud.activity.model.vo;


import com.arthur.cloud.activity.model.enums.ActivityEnums;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 活动vo
 * @create 2020-06-17 14:34
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class ActivityVo extends OperatorVo {


    private static final long serialVersionUID = 6633681437996653133L;
    /**
     * 活动标题
     */
    @ApiModelProperty(value = "活动标题")
    private String title;

    /**
     * 活动状态
     */
    @ApiModelProperty(value = "活动状态")
    private ActivityEnums type;

    /**
     * 封面图片
     */
    @ApiModelProperty(value = "封面图片")
    private String coverImages;

    /**
     * 详情页图片（逗号分割），最多5张
     */
    @ApiModelProperty(value = "详情页图片（逗号分割），最多5张")
    private String images;

    /**
     * 分享图片
     */
    @ApiModelProperty(value = "分享图片")
    private String shareImage;

    /**
     * 参加人数
     */
    @ApiModelProperty(value = "参加人数")
    private Integer joinCount;

    /**
     * 品牌id
     */
    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    /**
     * 开奖时间
     */
    @ApiModelProperty(value = "开奖时间")
    private Date openTime;

    /**
     * 幸运奖数量
     */
    @ApiModelProperty(value = "幸运奖数量")
    private Long luckyPrizeNum;

    /**
     * 幸运数字
     */
    @ApiModelProperty(value = "幸运数字")
    private Long luckyNumber;

    /**
     * 幸运奖名称
     */
    @ApiModelProperty(value = "幸运奖名称")
    private String luckyPrizeName;


    @ApiModelProperty(value = "奖品",dataType = "List")
    private List<PrizeVo> prizeList;
}
