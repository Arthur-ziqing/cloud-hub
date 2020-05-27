package com.arthur.cloud.common.mc;

import com.arthur.cloud.common.mc.adapter.rabbitmq.RabbitMqMessage;

public interface BussinessServiceHandler {

    MessageConsumerResult handler(RabbitMqMessage mqMessage)throws Exception;

    void handlerFailedMessage(RabbitMqMessage mqMessage);
}
