package br.com.dafiti.correlationid.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "spring.rabbitmq.consumers")
public class ConsumerProperties {

    private Config tracking;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Config {
        private String queue;

        private String routingKey;

        private String exchange;

        private String deadLetterQueue;

        private String deadLetterExchange;

        private String deadLetterRoutingKey;

        private boolean durable;

        private boolean exclusive;

        private boolean autoDelete;

        private String dlqQueueMode;

        private Integer concurrentConsumers;

        private Integer maxConcurrentConsumers;

        private boolean autostartup;

        private boolean requeueRejected;
    }
}
