package br.com.dafiti.correlationid.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class QueueConfig {

    @Autowired
    private ConsumerProperties consumerProperties;

    @Bean
    public Queue trackingQueue() {
        return createQueue(consumerProperties.getTracking());
    }

    @Bean
    public Queue trackingDLQ() {
        return createQueueDLQ(consumerProperties.getTracking());
    }

    @Bean
    public DirectExchange trackingExchange() {
        return new DirectExchange(consumerProperties.getTracking().getExchange(), true, false);
    }

    @Bean
    public Binding trackingBinding() {
        return BindingBuilder.bind(trackingQueue()).to(trackingExchange())
                .with(consumerProperties.getTracking().getRoutingKey());
    }



    private Queue createQueue(final ConsumerProperties.Config config) {
        final Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", config.getDeadLetterExchange());
        arguments.put("x-dead-letter-routing-key", config.getDeadLetterRoutingKey());
        return new Queue(
                config.getQueue(),
                config.isDurable(),
                config.isExclusive(),
                config.isAutoDelete(),
                arguments
        );
    }

    private Queue createQueueDLQ(final ConsumerProperties.Config config) {
        final Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-queue-mode", config.getDlqQueueMode());
        return new Queue(
                config.getDeadLetterQueue(),
                config.isDurable(),
                config.isExclusive(),
                config.isAutoDelete(),
                arguments
        );
    }
}
