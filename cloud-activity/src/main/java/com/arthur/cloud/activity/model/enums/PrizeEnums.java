package com.arthur.cloud.activity.model.enums;

public enum PrizeEnums {

    /**
     * 活动奖项
     */
    ACITICITY("活动奖"),
    /**
     * 幸运奖
     */
    LUCK("幸运奖"),
    /**
     * 邀请奖
     */
    INVITE("邀请奖");

    private String name;
    PrizeEnums(String name){this.name = name;}

    public String getName() {
        return name;
    }
}
