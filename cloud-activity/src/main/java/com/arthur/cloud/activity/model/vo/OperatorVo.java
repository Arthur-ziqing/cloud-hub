package com.arthur.cloud.activity.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 操作人vo
 * @create 2020-06-17 14:45
 * @Version 1.0
 **/
@ApiModel
public class OperatorVo implements Serializable {

    private static final long serialVersionUID = -1901667764059227907L;

    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人",required = true, dataType = "String")
    private String operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
