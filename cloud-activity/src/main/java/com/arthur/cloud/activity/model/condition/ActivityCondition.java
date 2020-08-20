package com.arthur.cloud.activity.model.condition;

import com.arthur.cloud.activity.model.enums.ActivityEnums;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description
 * @Author 秦梓青
 * @create 2020-08-09 16:19
 */
public class ActivityCondition extends PageCondition {

    @ApiModelProperty(value = "活动类型")
    private ActivityEnums activityType;

    public ActivityEnums getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityEnums activityType) {
        this.activityType = activityType;
    }
}
