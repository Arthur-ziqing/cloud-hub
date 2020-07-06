package com.arthur.cloud.activity.model.vo;

import com.arthur.cloud.activity.model.enums.PrizeEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 获奖信息
 * @create 2020-07-06 15:34
 * @Version 1.0
 **/
@Data
public class WinPrizeVo implements Serializable {

    private static final long serialVersionUID = -133723558346128725L;

    /**
     * 中奖号码
     */
    private Long num;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 奖项名称
     */
    private String prizeLevelName;

    /**
     * 中奖类型
     */
    private PrizeEnums prizeType;

}
