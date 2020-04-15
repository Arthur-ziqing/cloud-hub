package com.arthur.cloud.common.config.rabbitmq;

import com.arthur.cloud.common.mc.Producer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;

@Configuration
@ConditionalOnProperty(prefix = "spring.rabbitmq", value = "host")
public class RabbitmqProducerFactoryBean implements Producer {
    private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    @Autowired
    private RabbitTemplate template;

    @Override
    public void send(String topic, String routingKey, Serializable messageBody) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bout);
            oos.writeObject(messageBody);
        } catch (Exception e) {
            logger.error("err", e);
            logger.error("write object to stream failed! reason=" + e.getMessage());
        } finally {
            if (oos != null) {
                oos.close();
            }
        }

        try {
            template.send(topic, routingKey, new org.springframework.amqp.core.Message(bout.toByteArray(), new MessageProperties()));
        } catch (Exception e) {
            logger.error("err", e);
            logger.error("send message to queue failed! reason=" + e.getMessage());
        }
    }

    @Override
    public void boardcast(String topic, Serializable messageBody) throws IOException {
        send(topic,"",messageBody);
    }

    @Override
    public void send(String topic, String routingKey, String key, Serializable messageBody) throws IOException {
        logger.warn("key is ignored, while sending message with rabbitmq");
        send(topic,routingKey,messageBody);

    }
}
