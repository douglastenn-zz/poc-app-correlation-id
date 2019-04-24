package br.com.dafiti.correlationid.config;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class AmqpConfig implements RabbitListenerConfigurer {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private ConsumerProperties consumerProperties;

    @Bean
    public SimpleRabbitListenerContainerFactory trackingContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMaxConcurrentConsumers(consumerProperties.getTracking().getMaxConcurrentConsumers());
        factory.setConcurrentConsumers(consumerProperties.getTracking().getConcurrentConsumers());
        factory.setDefaultRequeueRejected(consumerProperties.getTracking().isRequeueRejected());
        factory.setAutoStartup(consumerProperties.getTracking().isAutostartup());
        factory.setAdviceChain(RetryInterceptorBuilder
                .stateless()
                .backOffOptions(1000, 2, 10000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .maxAttempts(1000)
                .build());
        return factory;
    }

    @Bean
    public MessageConverter jackson2Converter() {
        MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(jackson2Converter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar register) {
        register.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

}
