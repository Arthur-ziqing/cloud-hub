package com.arthur.cloud.common.mc.adapter.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description
 * @create 2020-04-02 10:48
 * @Version 1.0
 **/
public class RabbitMqMessage {

    private Message message;

    private String topic;

    private String tag;

    private byte[] bytes;

    private String key;

    public RabbitMqMessage(String topic, String tag, byte[] bytes) {
        this.topic = topic;
        this.tag = tag;
        this.bytes = bytes;
        message = new Message(bytes, new MessageProperties());
    }

    public RabbitMqMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public <T> T getObject(Class<T> clazz) throws IOException, ClassNotFoundException {
        ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(message.getBody()));
        Object obj = oin.readObject();
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        } else {
            return null;
        }
    }
}
