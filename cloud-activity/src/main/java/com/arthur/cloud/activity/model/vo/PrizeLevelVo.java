package com.arthur.cloud.activity.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 奖项vo
 * @create 2020-06-29 16:15
 * @Version 1.0
 **/
@Data
public class PrizeLevelVo implements Serializable {

    private static final long serialVersionUID = -2649519273223396889L;
    /**
     * 等级名称
     */
    private String levelName;

    /**
     * 奖品
     */
    private String levelPrize;

    /**
     * 邀请等级名称
     */
    private String inviteName;

    /**
     * 邀请奖
     */
    private String invitePrize;
}
