package br.com.dafiti.correlationid.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Publisher {

    private final RabbitTemplate publisher;

    public void sendMessage(Object message, String queue) {
        publisher.convertAndSend(queue, message);
    }
}
