package com.arthur.cloud.activity.model.enums;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 用户端活动列表枚举
 * @create 2020-06-18 14:10
 * @Version 1.0
 **/
public enum  UserActivityEnum {

    /**
     * 我参与的
     */
    JOIN("我参与的"),
    /**
     * 全部活动
     */
    ALL("全部活动"),
    /**
     * 往期活动
     */
    FINISH("往期活动");

    private String name;
    UserActivityEnum(String name){this.name = name;}

    public String getName() {
        return name;
    }
}
