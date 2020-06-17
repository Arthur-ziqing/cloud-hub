package com.arthur.cloud.activity.model.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 分页condition
 * @create 2020-06-17 11:43
 * @Version 1.0
 **/
@ApiModel(description = "分页查询")
public class PageCondition implements BaseCondition {

    private static final long serialVersionUID = 4126584147966710100L;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    private Integer start = 0;

    /**
     * 每页条数
     */
    @ApiModelProperty(value = "每页条数")
    private Integer limit = 10;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
