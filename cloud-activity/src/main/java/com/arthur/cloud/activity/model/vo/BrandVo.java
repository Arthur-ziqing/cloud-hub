package com.arthur.cloud.activity.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @Author 秦梓青
 * @ClassName
 * @Description 品牌数据接入vo
 * @create 2020-06-17 13:52
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "品牌")
public class BrandVo extends OperatorVo {


    private static final long serialVersionUID = 5971357424880260607L;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    /**
     * 品牌logo
     */
    @ApiModelProperty(value = "品牌logo")
    private String logo;

}
