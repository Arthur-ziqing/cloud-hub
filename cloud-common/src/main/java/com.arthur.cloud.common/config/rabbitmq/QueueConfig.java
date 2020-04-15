package com.arthur.cloud.common.config.rabbitmq;

import java.util.HashMap;
import java.util.Map;

public class QueueConfig {

    private String name;

    private Map<String, String> bindTos = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getBindTos() {
        return bindTos;
    }

    public void setBindTos(Map<String, String> bindTos) {
        this.bindTos = bindTos;
    }
}
