package com.arthur.cloud.activity.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author 秦梓青
 */
@Data
public class Brand implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 品牌名称
     */
    @Column(name = "brandName")
    private String brandName;

    /**
     * 品牌logo
     */
    private String logo;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createTime;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 更新时间
     */
    @Column(name = "updateTime")
    private Date updateTime;

}