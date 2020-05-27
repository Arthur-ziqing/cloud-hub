package com.arthur.cloud.common.mc;

public enum MessageConsumerResult {

    /**
     * 业务处理成功
     */
    Success,
    /**
     * 业务处理失败，稍后再次处理
     */
    TryLater,

    /**
     * 业务处理彻底失败
     */
    Failed,
}
