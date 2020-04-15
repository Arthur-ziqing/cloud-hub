package com.arthur.cloud.mc.config;

import com.arthur.cloud.common.mc.BussinessServiceHandler;
import com.arthur.cloud.common.mc.MessageConsumerResult;
import com.arthur.cloud.common.mc.adapter.rabbitmq.RabbitMqMessage;
import com.arthur.cloud.mc.annotation.MessageHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.UUID;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-04-01 18:10
 * @Version 1.0
 **/
@Configuration
@EnableRabbit
@ConditionalOnProperty(prefix = "spring.rabbitmq", value = "host", matchIfMissing = false)
@ConditionalOnClass(RabbitAdmin.class)
public class ConsumerFactoryBean implements RabbitListenerConfigurer {

    private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    private static final String BASE_PACKAGE = "com.arthur.cloud.mc.handler";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        Map<String, BussinessServiceHandler> serviceHandlerMap = applicationContext.getBeansOfType(BussinessServiceHandler.class);
        serviceHandlerMap.forEach((key, serviceHandler) -> {
            try {
                if (serviceHandler.getClass().isAnnotationPresent(MessageHandler.class)) {
                    MessageHandler messageHandler = serviceHandler.getClass().getAnnotation(MessageHandler.class);
                    String queueName = messageHandler.queueName();

                    SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
                    endpoint.setId(UUID.randomUUID().toString());
                    endpoint.setQueueNames(queueName);
                    endpoint.setMessageListener(message -> {
                        MessageConsumerResult result;
                        RabbitMqMessage message1 = new RabbitMqMessage(message);

                        try {
                            result = serviceHandler.handler(new RabbitMqMessage(message.getMessageProperties().getReceivedExchange(),
                                    message.getMessageProperties().getReceivedRoutingKey(),
                                    message.getBody()));

                            if (result == MessageConsumerResult.TryLater) {
                                logger.warn("serviceHandler [" + serviceHandler.getClass().getName() + "] return TRY_LATER! rabbitmq not support this action, treated as FAILED!");
                                serviceHandler.handlerFailedMessage(message1);
                            } else if (result == MessageConsumerResult.Failed) {
                                logger.warn("serviceHandler [" + serviceHandler.getClass().getName() + "] return FAILED!");
                                serviceHandler.handlerFailedMessage(message1);
                            } else {
                                logger.debug("serviceHandler [" + serviceHandler.getClass().getName() + "] return SUCCESS!");
                            }

                        } catch (Exception e) {
                            logger.error("BussinessServiceHandler throw unExpected exception!  exception=" + e.getMessage());
                            serviceHandler.handlerFailedMessage(message1);
                        }
                    });
                    rabbitListenerEndpointRegistrar.registerEndpoint(endpoint);
                } else {
                    throw new Exception(serviceHandler.getClass().getSimpleName() + "is not a type of  MessageHandler!");
                }
            } catch (Exception e) {
                logger.error("Can't register queue consumer, handler=" + serviceHandler.getClass().getSimpleName() + "\treason=" + e.getMessage());
            }

        });
    }
}
