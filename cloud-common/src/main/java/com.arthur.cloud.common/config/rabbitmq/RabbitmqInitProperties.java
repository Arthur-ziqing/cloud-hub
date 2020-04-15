package com.arthur.cloud.common.config.rabbitmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "rabbitmq.init")
@Configuration
public class RabbitmqInitProperties {

    private List<ExchangeConfig> exchanges;

    private List<QueueConfig> queues;

    public List<ExchangeConfig> getExchanges() {
        return exchanges;
    }

    public void setExchanges(List<ExchangeConfig> exchanges) {
        this.exchanges = exchanges;
    }

    public List<QueueConfig> getQueues() {
        return queues;
    }

    public void setQueues(List<QueueConfig> queues) {
        this.queues = queues;
    }
}
