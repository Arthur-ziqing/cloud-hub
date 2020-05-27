package com.arthur.cloud.common.config.rabbitmq;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Configuration
@ConditionalOnProperty(prefix = "spring.rabbitmq", value = "host", matchIfMissing = false)
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitmqConfig {
    private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    @Bean
    @ConditionalOnProperty(prefix = "spring.rabbitmq", name = "dynamic", matchIfMissing = true)
    @ConditionalOnMissingBean(AmqpAdmin.class)
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    ConnectionFactory connectionFactory(RabbitProperties config) throws Exception {
        RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
        if (config.getHost() != null) {
            factory.setHost(config.getHost());
            factory.setPort(config.getPort());
        }
        if (config.getUsername() != null) {
            factory.setUsername(config.getUsername());
        }
        if (config.getPassword() != null) {
            factory.setPassword(config.getPassword());
        }
        if (config.getVirtualHost() != null) {
            factory.setVirtualHost(config.getVirtualHost());
        }
        if (config.getRequestedHeartbeat() != null) {
            factory.setRequestedHeartbeat(config.getRequestedHeartbeat());
        }

        RabbitProperties.Ssl ssl = config.getSsl();
        if (ssl.isEnabled()) {
            factory.setUseSSL(true);
            factory.setKeyStore(ssl.getKeyStore());
            factory.setKeyStorePassphrase(ssl.getKeyStorePassword());
            factory.setTrustStore(ssl.getTrustStore());
            factory.setTrustStorePassphrase(ssl.getTrustStorePassword());
        }
        factory.afterPropertiesSet();
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
                factory.getObject());
        connectionFactory.setAddresses(config.getAddresses());

        //开启publisher confirms
        connectionFactory.setPublisherConfirms(false);

        return connectionFactory;
    }


    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (correlationData != null)
                logger.info("correlationData=" + correlationData.getId());
            logger.info("ack=" + ack);
            logger.info("cause=" + cause);
        });

        //开启publisher confirms，必须设置mandatoary为true
        rabbitTemplate.setMandatory(true);

        //如果使用 confirm，则必须关闭 transaction模式
        //rabbitTemplate.setChannelTransacted(true);

        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //设置并发的消费者

        //factory.setConcurrentConsumers(8);
        return factory;
    }

//    @Bean
//    public SimpleRabbitListenerContainerFactory qiRongContainerFactory(
//            SimpleRabbitListenerContainerFactoryConfigurer configurer,
//            ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConcurrentConsumers(2);
//        configurer.configure(factory, connectionFactory);
//        return factory;
//    }


    /**
     * @param rabbitAdmin
     */
    @Bean
    @ConditionalOnProperty(prefix = "rabbitmq.init", value = "enable", matchIfMissing = false)
    public Map<String, Exchange> initRabbitmq(RabbitAdmin rabbitAdmin, RabbitmqInitProperties rabbitmqInitProperties) throws Exception {

        Map<String, Exchange> exchangeMap = new HashMap<>();

        //declare exchange
        List<ExchangeConfig> exchangeConfigList = rabbitmqInitProperties.getExchanges();
        for (ExchangeConfig exchangeConfig : exchangeConfigList) {
            Exchange exchange;
            switch (exchangeConfig.getType()) {
                case ExchangeTypes.DIRECT:
                    exchange = new DirectExchange(exchangeConfig.getName(), exchangeConfig.getDurable(), exchangeConfig.getAutoDelete());
                    break;
                case ExchangeTypes.FANOUT:
                    exchange = new FanoutExchange(exchangeConfig.getName(), exchangeConfig.getDurable(), exchangeConfig.getAutoDelete());
                    break;
                case ExchangeTypes.TOPIC:
                    exchange = new TopicExchange(exchangeConfig.getName(), exchangeConfig.getDurable(), exchangeConfig.getAutoDelete());
                    break;
                default:
                    throw new Exception("Unsupported exchange type: " + exchangeConfig.getType());
            }

            rabbitAdmin.declareExchange(exchange);
            exchangeMap.put(exchangeConfig.getName(), exchange);
        }

        //declare queue and binding
        List<QueueConfig> queueConfigList = rabbitmqInitProperties.getQueues();
        for (QueueConfig queueConfig : queueConfigList) {
            Queue queue = new Queue(queueConfig.getName(), true);
            rabbitAdmin.declareQueue(queue);

            Map bindTos = queueConfig.getBindTos();
            Iterator<Map.Entry<String, String>> entries = bindTos.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> bindTo = entries.next();
                Exchange exchange = exchangeMap.get(bindTo.getKey());
                if (exchange == null)
                    throw new Exception("exchange not defined! exchangeName " + bindTo.getKey());

                rabbitAdmin.declareBinding(BindingBuilder
                        .bind(queue)
                        .to(exchange)
                        .with(bindTo.getValue())
                        .noargs()
                );
            }
        }

        return exchangeMap;
    }

}
