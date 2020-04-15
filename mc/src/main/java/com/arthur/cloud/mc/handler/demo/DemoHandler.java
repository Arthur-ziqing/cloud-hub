package com.arthur.cloud.mc.handler.demo;

import com.arthur.cloud.common.mc.BussinessServiceHandler;
import com.arthur.cloud.common.mc.MessageConsumerResult;
import com.arthur.cloud.common.mc.adapter.rabbitmq.RabbitMqMessage;
import com.arthur.cloud.common.mc.constant.RabbitmqConstant;
import com.arthur.cloud.mc.annotation.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-04-02 11:13
 * @Version 1.0
 **/
@Component
@MessageHandler(queueName = RabbitmqConstant.DEMO_QUEUE)
public class DemoHandler implements BussinessServiceHandler {

    private static final Logger logger = LoggerFactory.getLogger(DemoHandler.class);


    @Override
    public MessageConsumerResult handler(RabbitMqMessage mqMessage) throws Exception {
        logger.info(mqMessage.getKey());
        logger.info(mqMessage.getTag());
        logger.info(mqMessage.getTopic());

        return MessageConsumerResult.Success;
    }

    @Override
    public void handlerFailedMessage(RabbitMqMessage mqMessage) {

    }
}
