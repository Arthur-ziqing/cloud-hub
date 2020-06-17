package com.arthur.cloud.activity.model.enums;

public enum ActivityEnums {

    /**
     * 待发布
     */
    WAITING("待发布"),
    /**
     * 进行中
     */
    PROGRESS("进行中"),
    /**
     * 已结束
     */
    FINISH("已结束");

    private String name;
    ActivityEnums(String name){this.name = name;}

    public String getName() {
        return name;
    }
}
