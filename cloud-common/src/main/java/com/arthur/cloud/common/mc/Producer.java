package com.arthur.cloud.common.mc;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * 消息生产者
 */
public interface Producer {


    void boardcast(String topic, Serializable messageBody) throws IOException;

    void send(String topic, String routingKey, Serializable messageBody) throws IOException;

    void send(String topic, String routingKey, String key, Serializable messageBody) throws IOException;

}
