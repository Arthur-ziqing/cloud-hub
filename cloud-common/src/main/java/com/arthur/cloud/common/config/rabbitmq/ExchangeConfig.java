package com.arthur.cloud.common.config.rabbitmq;

import org.hibernate.validator.constraints.NotEmpty;


public class ExchangeConfig {

    @NotEmpty
    private String name;

    @NotEmpty
    private String type;

    private Boolean durable = true;

    private Boolean autoDelete = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getDurable() {
        return durable;
    }

    public void setDurable(Boolean durable) {
        this.durable = durable;
    }

    public Boolean getAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(Boolean autoDelete) {
        this.autoDelete = autoDelete;
    }
}
