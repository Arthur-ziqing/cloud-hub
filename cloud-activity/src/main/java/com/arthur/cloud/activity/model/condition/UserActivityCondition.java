package com.arthur.cloud.activity.model.condition;

import com.arthur.cloud.activity.model.enums.UserActivityEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-06-18 14:56
 * @Version 1.0
 **/
@ApiModel
public class UserActivityCondition extends PageCondition{

    private static final long serialVersionUID = -8856266533315693542L;
    @ApiModelProperty(value = "类型")
    private UserActivityEnum type;

    public UserActivityEnum getType() {
        return type;
    }

    public void setType(UserActivityEnum type) {
        this.type = type;
    }
}
