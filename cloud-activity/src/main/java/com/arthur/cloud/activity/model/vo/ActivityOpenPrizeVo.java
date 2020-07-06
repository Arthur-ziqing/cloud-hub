package com.arthur.cloud.activity.model.vo;

import com.arthur.cloud.activity.model.Prize;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 活动开奖vo
 * @create 2020-07-06 11:28
 * @Version 1.0
 **/
@Data
public class ActivityOpenPrizeVo implements Serializable {
    private static final long serialVersionUID = 3747663689440792485L;

    /**
     * 是否中奖
     */
    private boolean isWin;

    /**
     * 活动中奖者
     */
    private List<WinPrizeVo> wins;

    /**
     * 活动幸运奖中奖者
     */
    private List<WinPrizeVo> luckWins;
}
